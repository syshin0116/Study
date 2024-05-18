from tools.retriever import setup_retriever
from loaders.document_loader import load_documents
from embeddings.azure_embeddings import setup_embeddings

def main():
    urls = [
        'https://example.com/doc1',
        'https://example.com/doc2'
    ]
    # 문서 로딩
    documents = load_documents(urls)
    # 임베딩 설정
    embeddings = setup_embeddings()
    # 검색 도구 설정
    retriever = setup_retriever(embeddings)
    
    # 검색 실행
    results = retriever.retrieve("example query")
    print(results)

if __name__ == "__main__":
    main()