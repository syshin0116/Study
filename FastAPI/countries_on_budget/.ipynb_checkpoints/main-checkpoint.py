from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from fastapi.responses import FileResponse
from fastapi.middleware.cors import CORSMiddleware
from knn import KNNModel
import json

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

model = KNNModel('나라별 데이터 최종본123.csv')  # 모델 인스턴스 생성


class PredictionInput(BaseModel):
    days: str
    budget: str

@app.get("/")
async def read_root():
    return FileResponse('index.html')

@app.post("/predict")
async def predict(input_data: PredictionInput):
    try:
        # 데이터 검증 및 전처리
        days = int(input_data.days)
        budget = int(input_data.budget.replace(",", ""))  # 쉼표 제거 후 정수 변환
        
        # 모델로부터 예측 수행
        nearest_countries = model.predict(budget, days)
        # print(nearest_countries)

        # 예측 결과를 JSON 문자열로 변환
        prediction_json = nearest_countries[['country', 'total-expense', 'average-day-of-staying']].to_json(force_ascii=False)
        print(prediction_json)

        # JSON 문자열을 Python 객체로 변환
        prediction = json.loads(prediction_json)

        # 결과 반환
        return {"prediction": prediction}
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))




