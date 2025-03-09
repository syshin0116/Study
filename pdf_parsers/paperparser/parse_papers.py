import os
import sys
from pathlib import Path
from typing import List, Set
import json
from dotenv import load_dotenv, find_dotenv
import time
import traceback
from langgraph.graph import StateGraph
from langgraph.checkpoint.memory import MemorySaver
from langchain_core.runnables import RunnableConfig

# 상위 디렉토리 path 추가
notebook_dir = os.path.dirname(os.path.abspath(__file__))
app_path = os.path.abspath(os.path.join(notebook_dir, "../"))
if app_path not in sys.path:
    sys.path.insert(0, app_path)

from graphparser.state import GraphState
from workflow import create_workflow
from graphparser.process import process_document


def get_processed_files(processed_dir: str = "processed_documents") -> Set[str]:
    """이미 처리된 파일 목록을 가져옵니다."""
    processed = set()
    if os.path.exists(processed_dir):
        for file_path in Path(processed_dir).glob("*_docs.pkl"):
            # _docs.pkl을 제거하고 원본 파일명 복원
            original_file = file_path.stem.replace("_docs", "") + ".pdf"
            processed.add(original_file)
    return processed


def process_directory(
    input_dir: str,
    language: str = "Korean",
    processed_dir: str = "processed_documents",
    metadata_dir: str = "paper_metadata",
    progress_file: str = "processing_progress.json",
) -> None:
    """
    디렉토리 내의 모든 PDF 파일을 처리합니다.

    Args:
        input_dir: PDF 파일이 있는 디렉토리 경로
        language: 문서 언어
        processed_dir: 처리된 문서가 저장될 디렉토리
        metadata_dir: 메타데이터가 저장될 디렉토리
        progress_file: 진행 상황을 저장할 파일
    """
    # 진행 상황 로드
    if os.path.exists(progress_file):
        with open(progress_file, "r", encoding="utf-8") as f:
            progress = json.load(f)
    else:
        progress = {
            "processed_files": [],
            "failed_files": [],
            "last_successful_file": None,
        }

    # 이미 처리된 파일 목록 가져오기
    processed_files = get_processed_files(processed_dir)

    # PDF 파일 목록 가져오기
    pdf_files = [f for f in os.listdir(input_dir) if f.endswith(".pdf")]
    total_files = len(pdf_files)

    print(f"총 {total_files}개의 PDF 파일을 처리합니다.")
    print(f"이미 처리된 파일: {len(processed_files)}개")

    for i, pdf_file in enumerate(pdf_files, 1):
        if pdf_file in processed_files or pdf_file in progress["processed_files"]:
            print(f"[{i}/{total_files}] {pdf_file} - 이미 처리됨, 건너뜁니다.")
            continue

        if pdf_file in progress["failed_files"]:
            print(
                f"[{i}/{total_files}] {pdf_file} - 이전에 실패한 파일, 다시 시도합니다."
            )

        print(f"[{i}/{total_files}] {pdf_file} 처리 중...")

        try:
            # 환경 변수 새로 로드 (API 키 갱신을 위해)
            load_dotenv(find_dotenv(), override=True)
            if not os.getenv("UPSTAGE_API_KEY"):
                raise ValueError("UPSTAGE_API_KEY가 설정되지 않았습니다.")

            # 워크플로우 생성 및 실행
            filepath = os.path.join(input_dir, pdf_file)
            app = create_workflow()

            config = RunnableConfig(
                recursion_limit=100,
                configurable={"thread_id": f"research-paper-{pdf_file}"},
            )

            inputs = GraphState(filepath=filepath, language=language)

            # 워크플로우 실행
            for output in app.stream(inputs, config=config):
                pass  # 진행 상황은 필요한 경우 여기서 처리

            # 상태 가져오기
            state = app.get_state(config).values

            # 문서 처리 및 저장
            markdowns, all_docs = process_document(state)

            # 마크다운 파일 저장
            markdown_path = os.path.join(input_dir, pdf_file.replace(".pdf", ".md"))
            with open(markdown_path, "w", encoding="utf-8") as f:
                f.write("\n\n".join(markdowns))

            # 진행 상황 업데이트
            progress["processed_files"].append(pdf_file)
            if pdf_file in progress["failed_files"]:
                progress["failed_files"].remove(pdf_file)
            progress["last_successful_file"] = pdf_file

            # 진행 상황 저장
            with open(progress_file, "w", encoding="utf-8") as f:
                json.dump(progress, f, ensure_ascii=False, indent=2)

            print(f"[{i}/{total_files}] {pdf_file} 처리 완료")

        except Exception as e:
            print(f"[{i}/{total_files}] {pdf_file} 처리 중 오류 발생:")
            print(traceback.format_exc())

            # 실패한 파일 기록
            if pdf_file not in progress["failed_files"]:
                progress["failed_files"].append(pdf_file)

            # 진행 상황 저장
            with open(progress_file, "w", encoding="utf-8") as f:
                json.dump(progress, f, ensure_ascii=False, indent=2)

            # API 키 만료 오류인 경우
            if "UPSTAGE_API_KEY" in str(e) or "api_key" in str(e).lower():
                print(
                    "API 키가 만료되었습니다. 새로운 API 키를 .env 파일에 설정한 후 프로그램을 다시 실행해주세요."
                )
                break

            # 다른 오류의 경우 잠시 대기 후 계속
            time.sleep(5)
            continue

    print("\n처리 완료!")
    print(f"성공: {len(progress['processed_files'])}개")
    print(f"실패: {len(progress['failed_files'])}개")
    if progress["failed_files"]:
        print("\n실패한 파일 목록:")
        for failed_file in progress["failed_files"]:
            print(f"- {failed_file}")


# 사용 예시:
if __name__ == "__main__":
    INPUT_DIR = "./data"  # PDF 파일이 있는 디렉토리
    process_directory(INPUT_DIR, language="Korean")
