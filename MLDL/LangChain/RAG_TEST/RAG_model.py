import dotenv
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import WebBaseLoader
from langchain_community.vectorstores import Chroma
from langchain_openai.embeddings.azure import AzureOpenAIEmbeddings
from langchain.retrievers import BM25Retriever, EnsembleRetriever
import bs4
from langchain import hub
from langchain_community.document_loaders import WebBaseLoader
from langchain_chroma import Chroma
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnablePassthrough
from langchain_openai import OpenAIEmbeddings
from langchain_text_splitters import RecursiveCharacterTextSplitter
dotenv.load_dotenv()
from langchain_openai import AzureChatOpenAI


gpt_3 = 'dev-gpt-35-turbo-sample'
embedding_model = 'text-embedding-0414' 
gpt_4 = 'gpt-4-32k'

llm = AzureChatOpenAI(model=gpt_4, temperature=0)

urls = [
    'https://www.110.go.kr/consult/affect.do'

    # 'https://www.law.go.kr/lsInfoP.do?lsiSeq=39593&efYd=19971231#0000',
    # 'https://www.law.go.kr/lsInfoP.do?lsiSeq=258015&efYd=20240101#0000',
    # 'https://www.law.go.kr/lsInfoP.do?lsiSeq=260889&efYd=20240301#0000',
    # 'https://www.law.go.kr/lsInfoP.do?lsiSeq=261251&efYd=20240322#0000'
]

human_question = '''갑질 유형 중에 공공분야 내부의 갑질에 대해 설명해줘'''

# Load, chunk and index the contents of the blog.
docs = [WebBaseLoader(url).load() for url in urls]
docs_list = [item for sublist in docs for item in sublist]

text_splitter = RecursiveCharacterTextSplitter.from_tiktoken_encoder(
    chunk_size=150, chunk_overlap=50, 
)
splits = text_splitter.split_documents(docs_list)


bm25_retriever = BM25Retriever.from_documents(splits)
bm25_retriever.k = 6

vectorstore = Chroma.from_documents(documents=splits, embedding=AzureOpenAIEmbeddings(model=embedding_model))

# initialize the ensemble retriever
ensemble_retriever = EnsembleRetriever(
    retrievers=[bm25_retriever, vectorstore.as_retriever(search_type="similarity")], weights=[0.3, 0.7]
)


prompt = hub.pull("rlm/rag-prompt")

def format_docs(docs):
    return "\n\n".join(doc.page_content for doc in docs)


rag_chain = (
    {"context": ensemble_retriever | format_docs, "question": RunnablePassthrough()}
    | prompt
    | llm
    | StrOutputParser()
)

response = rag_chain.invoke(human_question)

return response

