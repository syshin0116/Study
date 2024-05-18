from langchain_community.document_loaders import WebBaseLoader

def load_documents(urls):
    """주어진 URL 리스트로부터 문서를 로딩합니다."""
    docs = [WebBaseLoader(url).load() for url in urls]
    docs_list = [item for sublist in docs for item in sublist]
    return docs_list
