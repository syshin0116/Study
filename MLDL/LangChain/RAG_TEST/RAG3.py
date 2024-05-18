from dotenv import load_dotenv
load_dotenv()

gpt_35_model = 'dev-gpt-35-turbo-sample'
embedding_model = 'dev-text-embedding-ada-002-01' 
urls = [
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=39593&efYd=19971231#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=258015&efYd=20240101#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=260889&efYd=20240301#0000',
    'https://www.law.go.kr/lsInfoP.do?lsiSeq=261251&efYd=20240322#0000'
]

human_question = '''소득세법 제2조(납세의무) 에 대해 설명해줘'''

from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import WebBaseLoader
from langchain_community.vectorstores import Chroma
from langchain_openai.embeddings.azure import AzureOpenAIEmbeddings

docs = [WebBaseLoader(url).load() for url in urls]
docs_list = [item for sublist in docs for item in sublist]

text_splitter = RecursiveCharacterTextSplitter.from_tiktoken_encoder(
    chunk_size=100, chunk_overlap=50, 
)
doc_splits = text_splitter.split_documents(docs_list)

# 벡터 데이터베이스에 문서 추가
vectorstore = Chroma.from_documents(
    documents=doc_splits,
    collection_name="rag-chroma",
    embedding=AzureOpenAIEmbeddings(
        model=embedding_model# Azure OpenAIEmbedding model명
        ),
    persist_directory="./chroma_db"
)
retriever = vectorstore.as_retriever()


documents = vectorstore.similarity_search(human_question)

for doc in documents:
    print(doc)  # Inspect the contents of each document
