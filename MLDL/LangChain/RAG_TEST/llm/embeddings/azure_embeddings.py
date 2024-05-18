from langchain_openai.embeddings.azure import AzureOpenAIEmbeddings
from config import AZURE_EMBEDDING_MODEL

def setup_embeddings():
    """Azure 임베딩을 설정하고 반환합니다."""
    return AzureOpenAIEmbeddings(model=AZURE_EMBEDDING_MODEL)