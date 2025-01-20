from typing import TypedDict, Annotated, List, Dict
import operator
from .element import Element
from langchain_core.documents import Document


class ParseState(TypedDict):
    filepath: Annotated[str, "filepath"]  # 원본 파일 경로
    filetype: Annotated[
        str, "filetype"
    ]  # 파일 타입(PDF, DOCX, PPTX, XLSX) / 현재 PDF만 지원
    split_filepaths: Annotated[List[str], "split_filepaths"]  # 분할한 파일 경로
    working_filepath: Annotated[str, "working_filepath"]  # 현재 작업중인 파일

    metadata: Annotated[
        List[Dict], operator.add
    ]  # parsing metadata (api, model, usage)

    total_cost: Annotated[float, "total_cost"]  # 총 비용

    raw_elements: Annotated[List[Dict], operator.add]  # raw elements from Upstage
    elements_from_parser: Annotated[
        List[Dict], "elements_from_parser"
    ]  # elements after post-processing

    elements: Annotated[List[Element], "elements"]  # Final cleaned elements
    reconstructed_elements: Annotated[
        List[Dict], "reconstructed_elements"
    ]  # reconstructed elements

    export: Annotated[List, operator.add]  # export results

    texts_by_page: Annotated[Dict[int, str], "texts_by_page"]  # texts by page
    images_by_page: Annotated[
        Dict[int, List[Element]], "images_by_page"
    ]  # images by page

    tables_by_page: Annotated[
        Dict[int, List[Element]], "tables_by_page"
    ]  # tables by page

    extracted_image_entities: Annotated[
        List[Element], "extracted_image_entities"
    ]  # extracted image entities

    extracted_table_entities: Annotated[
        List[Element], "extracted_table_entities"
    ]  # extracted table entities

    documents: Annotated[List[Document], "documents"]  # documents

    language: Annotated[str, "language"]  # language
