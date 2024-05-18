from langchain.tools.retriever import create_retriever_tool
from langchain_community.vectorstores import Chroma
from embeddings.azure_embeddings import setup_embeddings
from langchain.text_splitter import RecursiveCharacterTextSplitter

def setup_retriever(embeddings):
    """임베딩을 기반으로 검색 도구를 설정합니다."""
    text_splitter = RecursiveCharacterTextSplitter.from_tiktoken_encoder(
        chunk_size=100, chunk_overlap=50
    )
    
    # 임베딩과 텍스트 스플리터로 문서를 준비
    doc_splits = text_splitter.split_documents(docs_list)

    # 벡터스토어 생성
    vectorstore = Chroma.from_documents(
        documents=doc_splits,
        collection_name="rag-chroma",
        embedding=embeddings,
    )
    
    # 검색 도구 생성
    retriever = vectorstore.as_retriever()
    return create_retriever_tool(
        retriever,
        "retrieve_blog_posts",
        "Search and return information about specific topics."
    )