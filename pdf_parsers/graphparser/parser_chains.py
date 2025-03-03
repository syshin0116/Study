from langchain_openai import ChatOpenAI
from langchain_core.runnables import chain
from .models import MultiModal
from .state import GraphState
from typing import Optional
from typing_extensions import TypedDict


@chain
def extract_image_summary(data_batches):
    # 객체 생성
    llm = ChatOpenAI(
        temperature=0,  # 창의성 (0.0 ~ 2.0)
        model_name="gpt-4o-mini",  # 모델명
    )

    system_prompt = """You are an expert in extracting useful information from IMAGE.
With a given image, your task is to extract key entities, summarize them, and write useful information that can be used later for retrieval.
Also, provide five hypothetical questions based on the image that users can ask.
"""

    image_paths = []
    system_prompts = []
    user_prompts = []

    for data_batch in data_batches:
        context = data_batch["text"]
        image_path = data_batch["image"]
        language = data_batch["language"]
        user_prompt_template = f"""Here is the context related to the image: {context}
        
###

Output Format:

<image>
<title>
[title]
</title>
<summary>
[summary]
</summary>
<entities> 
[entities]
</entities>
<hypothetical_questions>
[hypothetical_questions]
</hypothetical_questions>
</image>

Output must be written in {language}.
"""
        image_paths.append(image_path)
        system_prompts.append(system_prompt)
        user_prompts.append(user_prompt_template)

    # 멀티모달 객체 생성
    multimodal_llm = MultiModal(llm)

    # 이미지 파일로 부터 질의
    answer = multimodal_llm.batch(
        image_paths, system_prompts, user_prompts, display_image=False
    )
    return answer


@chain
def extract_table_summary(data_batches):
    # 객체 생성
    llm = ChatOpenAI(
        temperature=0,  # 창의성 (0.0 ~ 2.0)
        model_name="gpt-4o-mini",  # 모델명
    )

    system_prompt = """You are an expert in extracting useful information from TABLE. 
Given a Markdown table and an original image corresponding to the table, your task is to extract key entities, summarize them, and write relevant information that can be useful for future retrieval.
If the numbers are present, summarize important insights from the numbers.
Also, provide five hypothetical questions based on the image that users can ask.
"""

    image_paths = []
    system_prompts = []
    user_prompts = []

    for data_batch in data_batches:
        context = data_batch["text"]
        table_markdown = data_batch["table_markdown"]
        table_html = data_batch["table_html"]
        image_path = data_batch["table"]
        language = data_batch["language"]
        user_prompt_template = f"""Here is the markdown format of the table. <markdown>{table_markdown}</markdown> <html>{table_html}</html>
        
Here is the context related to the image of table: {context}
        
###

Output Format:

<table>
<title>
[title]
</title>
<summary>
[summary]
</summary>
<entities> 
[entities]
</entities>
<data_insights>
[data_insights]
</data_insights>
<hypothetical_questions>
[hypothetical_questions]
</hypothetical_questions>
</table>

Output must be written in {language}.
"""
        image_paths.append(image_path)
        system_prompts.append(system_prompt)
        user_prompts.append(user_prompt_template)

    # 멀티모달 객체 생성
    multimodal_llm = MultiModal(llm)

    # 이미지 파일로 부터 질의
    answer = multimodal_llm.batch(
        image_paths, system_prompts, user_prompts, display_image=False
    )
    return answer


@chain
def table_markdown_extractor(data_batches):
    # 객체 생성
    llm = ChatOpenAI(
        temperature=0,  # 창의성 (0.0 ~ 2.0)
        model_name="gpt-4o-mini",  # 모델명
    )

    system_prompt = "Given an image of a table and its corresponding Markdown table, your task is to combine both sources to accurately extract all information and ensure the table is represented in a complete Markdown format. Do not narrate; only respond with the Markdown table."

    image_paths = []
    system_prompts = []
    user_prompts = []

    for data_batch in data_batches:
        table_markdown = data_batch["table_markdown"]
        image_path = data_batch["table"]
        user_prompt_template = f"""DO NOT wrap your answer in ```markdown``` or any XML tags.

Here is the markdown format of the table: 
{table_markdown}

### Output Format:

<table_markdown>

Output must be written in Korean.
"""
        image_paths.append(image_path)
        system_prompts.append(system_prompt)
        user_prompts.append(user_prompt_template)

    # 멀티모달 객체 생성
    multimodal_llm = MultiModal(llm)

    # 이미지 파일로 부터 질의
    answer = multimodal_llm.batch(
        image_paths, system_prompts, user_prompts, display_image=False
    )
    return answer


@chain
def table_json_extractor(data_batches):
    # 객체 생성
    llm = ChatOpenAI(
        temperature=0,  # 창의성 (0.0 ~ 2.0)
        model_name="gpt-4o-mini",  # 모델명
    )

    system_prompt = "Given an image of a table and its corresponding Markdown table and HtML table, your task is to combine all sources to accurately extract all information and ensure the table is represented in a structured JSON format. Do not narrate; only respond with the JSON output."

    image_paths = []
    system_prompts = []
    user_prompts = []

    for data_batch in data_batches:
        table_markdown = data_batch["table_markdown"]
        table_html = data_batch["table_html"]
        image_path = data_batch["table"]
        user_prompt_template = f"""DO NOT wrap your answer in ```json``` or any XML tags.

Here is the markdown format of the table: 
{table_markdown}

Here is the HTML format of the table:
{table_html}

###

Output Format (JSON):

{{
  "columns": ["column_1", "column_2", "column_3", ...],
  "rows": [
    {{"column_1": "value_1", "column_2": "value_2", "column_3": "value_3", ...}},
    {{"column_1": "value_4", "column_2": "value_5", "column_3": "value_6", ...}},
    ...
  ]
}}

Output must be written in Korean.
"""
        image_paths.append(image_path)
        system_prompts.append(system_prompt)
        user_prompts.append(user_prompt_template)

    # 멀티모달 객체 생성
    multimodal_llm = MultiModal(llm)

    # 이미지 파일로 부터 질의
    answer = multimodal_llm.batch(
        image_paths, system_prompts, user_prompts, display_image=False
    )
    return answer


@chain
def extract_metadata_from_research_paper(data_batches):
    class ResearchPaperMetadata(TypedDict):
        title: dict[str, Optional[str]]  # MultilingualText
        authors: dict[str, list[str]]  # MultilingualList
        abstract: dict[str, Optional[str]]  # MultilingualText
        keywords: dict[str, list[str]]  # MultilingualList
        publication_date: str
        language: list[str]

    # 객체 생성
    llm = ChatOpenAI(
        temperature=0,  # 창의성 (0.0 ~ 2.0)
        model_name="gpt-4o-mini",  # 모델명
    )

    prompt = f"""You are an expert in extracting metadata from a research paper. Given the first page of a research paper, your task is to extract metadata in both Korean and English when available.

For each field, provide both Korean and English versions if they exist. Structure the output as follows:
- Title: Both Korean and English titles
- Authors: List of authors in both Korean and English
- Abstract: Both Korean and English abstracts
- Keywords: List of keywords in both Korean and English
- Publication Date: In YYYY-MM-DD format
- Language: List of languages used in the paper (e.g., ["ko", "en"])

Return null for any language version that doesn't exist.

Extract metadata from this research paper first page:

Text Elements:
{data_batches["text_elements"]}

Full Text:
{data_batches["full_text"]}

Page Summary:
{data_batches["page_summary"]}

Document Language: {data_batches["language"]}"""

    # Create a structured output chain and invoke it
    return llm.with_structured_output(ResearchPaperMetadata).invoke(prompt)
