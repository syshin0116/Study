from pathlib import Path
from typing import List, Dict, Tuple
from langchain.schema import Document
from langchain_text_splitters import RecursiveCharacterTextSplitter
from bs4 import BeautifulSoup
import os
import re


def create_document(content, metadata):
    """문서 객체를 생성합니다."""
    return Document(page_content=content, metadata=metadata)


def html_to_markdown(html: str) -> str:
    """HTML을 마크다운으로 변환합니다."""
    soup = BeautifulSoup(html, "html.parser")
    markdown_lines = []

    def get_heading_level_from_font_size(font_size: int) -> int:
        if font_size == 22:
            return 1
        elif font_size == 20:
            return 2
        return 0

    for child in soup.contents:
        if not hasattr(child, "name") or child.name is None:
            text = child.get_text(strip=True)
            if text:
                markdown_lines.append(text)
            continue

        tag_name = child.name.lower()
        style_attr = child.get("style", "")
        match = re.search(r"font-size:\s*(\d+)px", style_attr)
        font_size = int(match.group(1)) if match else 0

        for br in child.find_all("br"):
            br.replace_with(" ")

        text_content = " ".join(child.stripped_strings).strip()
        if not text_content:
            continue

        if tag_name == "h1":
            heading_level = 1
        else:
            heading_level = get_heading_level_from_font_size(font_size)

        if tag_name in ["footer", "header"]:
            continue

        if heading_level == 1:
            markdown_lines.append(f"# {text_content}")
        elif heading_level == 2:
            markdown_lines.append(f"## {text_content}")
        else:
            markdown_lines.append(text_content + "\n")

    return "\n".join(markdown_lines)


def process_text_element(element: dict) -> str:
    """텍스트 요소를 처리합니다."""
    html_content = element["content"]["html"]
    return html_to_markdown(html_content)


def extract_tag_content(content, tag):
    """태그 내용을 추출합니다."""
    pattern = rf"<{tag}>(.*?)</{tag}>"
    match = re.search(pattern, content, re.DOTALL)
    return match.group(1).strip() if match else None


def extract_non_tag_content(content, tag):
    """태그를 제외한 내용을 추출합니다."""
    pattern = rf"<{tag}>.*?</{tag}>"
    result = re.sub(pattern, "", content, flags=re.DOTALL)
    return result.strip()


def process_image_element(element, state, page_number):
    """이미지 요소를 처리합니다."""
    image_id = element["id"]
    image_summary = state["image_summary"][image_id]
    image_path = state["images"][image_id]
    image_path_md = f"![{image_path}]({image_path})"

    markdown = f"{image_path_md}"

    image_summary_clean = extract_non_tag_content(
        image_summary, "hypothetical_questions"
    )

    docs = [
        create_document(
            image_summary_clean,
            {
                "type": "image",
                "image": image_path,
                "page": page_number,
                "source": state["filepath"],
                "id": image_id,
            },
        )
    ]

    hypo_docs = []
    hypothetical_questions = extract_tag_content(
        image_summary, "hypothetical_questions"
    )
    if hypothetical_questions is not None:
        hypo_docs.append(
            create_document(
                hypothetical_questions,
                {
                    "type": "hypothetical_questions",
                    "image": image_path,
                    "summary": image_summary_clean,
                    "page": page_number,
                    "source": state["filepath"],
                    "id": image_id,
                },
            )
        )

    return markdown, docs, hypo_docs


def process_table_element(element, state, page_number):
    """테이블 요소를 처리합니다."""
    table_id = element["id"]
    table_summary = state["table_summary"][table_id]
    table_markdown = state["table_markdown"][table_id]
    table_path = state["tables"][table_id]
    table_path_md = f"![{table_path}]({table_path})"

    markdown = f"{table_path_md}\n{table_markdown}"

    table_summary_clean = extract_non_tag_content(
        table_summary, "hypothetical_questions"
    )

    docs = [
        create_document(
            table_summary_clean,
            {
                "type": "table",
                "table": table_path,
                "markdown": table_markdown,
                "page": page_number,
                "source": state["filepath"],
                "id": table_id,
            },
        )
    ]

    hypo_docs = []
    hypothetical_questions = extract_tag_content(
        table_summary, "hypothetical_questions"
    )
    if hypothetical_questions is not None:
        hypo_docs.append(
            create_document(
                hypothetical_questions,
                {
                    "type": "hypothetical_questions",
                    "table": table_path,
                    "summary": table_summary_clean,
                    "markdown": table_markdown,
                    "page": page_number,
                    "source": state["filepath"],
                    "id": table_id,
                },
            )
        )

    return markdown, docs, hypo_docs


def process_page(page, state, page_number, text_splitter):
    """페이지를 처리합니다."""
    markdowns = []
    docs = []
    hypo_docs = []
    page_texts = []

    for element in page["elements"]:
        if element["category"] == "figure":
            markdown, element_docs, hypo_doc = process_image_element(
                element, state, page_number
            )
            markdowns.append(markdown)
            docs.extend(element_docs)
            hypo_docs.extend(hypo_doc)
        elif element["category"] == "table":
            markdown, element_docs, hypo_doc = process_table_element(
                element, state, page_number
            )
            markdowns.append(markdown)
            docs.extend(element_docs)
            hypo_docs.extend(hypo_doc)
        else:
            text = process_text_element(element)
            markdowns.append(text)
            page_texts.append(text)

    page_text = "\n".join(page_texts)
    split_texts = text_splitter.split_text(page_text)

    text_summary = state["text_summary"][page_number]

    docs.append(
        create_document(
            text_summary,
            metadata={
                "type": "page_summary",
                "page": page_number,
                "source": state["filepath"],
                "text": page_text,
            },
        )
    )

    for text in split_texts:
        docs.append(
            create_document(
                text,
                metadata={
                    "type": "text",
                    "page": page_number,
                    "source": state["filepath"],
                    "summary": text_summary,
                },
            )
        )

    return markdowns, docs, hypo_docs


def process_document(state) -> Tuple[List[str], List[Document]]:
    """전체 문서를 처리합니다."""
    markdowns = []
    docs = []
    hypo_docs = []
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=50)

    # 파일명을 paper_id로 사용
    paper_id = os.path.basename(state["filepath"]).replace(".pdf", "")

    # 기본 논문 식별 메타데이터
    base_paper_metadata = {
        "paper_id": paper_id,
    }

    # metadata가 있고 title이 있는 경우에만 title 추가
    if "metadata" in state and isinstance(state["metadata"], dict):
        metadata = state["metadata"]
        if (
            "title" in metadata
            and isinstance(metadata["title"], dict)
            and "ko" in metadata["title"]
        ):
            base_paper_metadata["title"] = metadata["title"]["ko"]
        elif "title" in metadata:
            base_paper_metadata["title"] = str(metadata["title"])

    # 각 페이지 처리
    for page_number, page in state["page_elements"].items():
        page_markdowns, page_docs, page_hypo_docs = process_page(
            page, state, page_number, text_splitter
        )
        markdowns.extend(page_markdowns)
        docs.extend(page_docs)
        hypo_docs.extend(page_hypo_docs)

    # 모든 문서에 기본 논문 식별 메타데이터 추가
    all_docs = docs + hypo_docs
    for doc in all_docs:
        doc.metadata.update(base_paper_metadata)

    # 문서와 메타데이터 저장
    docs_dir = "processed_documents"
    save_documents(all_docs, f"{docs_dir}/{paper_id}_docs.pkl")

    # metadata가 있는 경우에만 저장
    if "metadata" in state and isinstance(state["metadata"], dict):
        save_paper_metadata(paper_id, state["metadata"])

    return markdowns, all_docs


def save_documents(documents: List[Document], filepath: str):
    """Langchain Document 리스트를 파일로 저장합니다."""
    import pickle

    Path(filepath).parent.mkdir(parents=True, exist_ok=True)
    with open(filepath, "wb") as f:
        pickle.dump(documents, f)


def save_paper_metadata(
    paper_id: str, metadata: dict, output_dir: str = "paper_metadata"
):
    """논문 메타데이터를 저장합니다."""
    import pickle

    Path(output_dir).mkdir(parents=True, exist_ok=True)
    with open(f"{output_dir}/{paper_id}_metadata.pkl", "wb") as f:
        pickle.dump(metadata, f)


def load_documents(filepath: str) -> List[Document]:
    """파일에서 Langchain Document 리스트를 불러옵니다."""
    import pickle

    with open(filepath, "rb") as f:
        return pickle.load(f)


def load_paper_metadata(paper_id: str, metadata_dir: str = "paper_metadata") -> dict:
    """특정 논문의 메타데이터를 불러옵니다."""
    import pickle

    with open(f"{metadata_dir}/{paper_id}_metadata.pkl", "rb") as f:
        return pickle.load(f)


def load_all_paper_metadata(metadata_dir: str = "paper_metadata") -> Dict[str, dict]:
    """모든 논문의 메타데이터를 불러옵니다."""
    metadata_store = {}
    for file_path in Path(metadata_dir).glob("*_metadata.pkl"):
        paper_id = file_path.stem.replace("_metadata", "")
        metadata_store[paper_id] = load_paper_metadata(paper_id)
    return metadata_store
