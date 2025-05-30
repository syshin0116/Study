import os
from openai import AzureOpenAI

# Azure OpenAI 설정 (직접 입력하거나 환경변수 사용)
client = AzureOpenAI(
    api_key="YOUR_API_KEY",  # 여기에 실제 API 키 입력
    api_version="2024-02-01",
    azure_endpoint="https://YOUR_RESOURCE_NAME.openai.azure.com/"  # 여기에 실제 엔드포인트 입력
)

# Chat 테스트
print("Chat 테스트 중...")
try:
    response = client.chat.completions.create(
        model="gpt-35-turbo",  # 배포한 모델 이름
        messages=[{"role": "user", "content": "안녕하세요!"}],
        max_tokens=50
    )
    print("Chat 성공:", response.choices[0].message.content)
except Exception as e:
    print("Chat 실패:", e)

# Embedding 테스트
print("\nEmbedding 테스트 중...")
try:
    response = client.embeddings.create(
        model="text-embedding-ada-002",  # 배포한 임베딩 모델 이름
        input="테스트 문장입니다."
    )
    embedding = response.data[0].embedding
    print(f"Embedding 성공: 차원={len(embedding)}, 첫 3개 값={embedding[:3]}")
except Exception as e:
    print("Embedding 실패:", e) 