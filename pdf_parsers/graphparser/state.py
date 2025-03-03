from typing import Optional
from typing_extensions import TypedDict


# GraphState 상태를 저장하는 용도로 사용합니다.
class MultilingualText(TypedDict):
    """Text content in multiple languages"""

    ko: Optional[str]  # Korean text
    en: Optional[str]  # English text


class MultilingualList(TypedDict):
    """List content in multiple languages"""

    ko: list[str]  # Korean list
    en: list[str]  # English list


class PaperMetadata(TypedDict):
    """Paper metadata extracted from the first page with multilingual support"""

    title: MultilingualText  # 논문 제목 (한/영)
    authors: MultilingualList  # 저자 목록 (한/영)
    abstract: MultilingualText  # 초록 (한/영)
    keywords: MultilingualList  # 키워드 (한/영)
    publication_date: str  # 출판일
    language: list[str]  # 논문 작성 언어 목록 (e.g., ["ko", "en"])


class GraphState(TypedDict):
    # Basic file information
    filepath: str  # 파일 경로
    filetype: str  # 파일 타입 (pdf)

    # Paper metadata
    metadata: PaperMetadata  # 논문 메타데이터

    # Processing information
    page_numbers: list[int]  # 페이지 번호 목록
    batch_size: int  # 배치 크기
    split_filepaths: list[str]  # 분할된 파일 경로
    analyzed_files: list[str]  # 분석된 파일 목록

    # Content elements
    page_elements: dict[int, dict[str, list[dict]]]  # 페이지 요소
    page_metadata: dict[int, dict]  # 페이지별 메타데이터
    page_summary: dict[int, str]  # 페이지 요약

    # Images
    images: list[str]  # 이미지 파일 경로
    image_summary: list[str]  # 이미지 요약

    # Tables
    tables: list[str]  # 표 파일 경로
    table_summary: dict[int, str]  # 표 요약
    table_markdown: dict[int, str]  # 표 마크다운
    table_html: dict[int, str]  # 표 HTML
    table_json: dict[int, str]  # 표 JSON

    # Text content
    texts: list[str]  # 텍스트 내용
    text_summary: dict[int, str]  # 텍스트 요약

    # Additional processing data
    table_summary_data_batches: list[dict]  # 표 요약 데이터 배치
    language: str  # 문서 언어
