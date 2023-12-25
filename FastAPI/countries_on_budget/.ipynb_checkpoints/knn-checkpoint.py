# knn.py
import pandas as pd
import numpy as np
from sklearn.neighbors import NearestNeighbors
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt

# IQR 방법을 사용하여 이상치 제거
def remove_outliers_iqr(data, column):
    Q1 = data[column].quantile(0.25)
    Q3 = data[column].quantile(0.75)
    IQR = Q3 - Q1
    lower_bound = Q1 - 1.5 * IQR
    upper_bound = Q3 + 1.5 * IQR
    data = data[(data[column] >= lower_bound) & (data[column] <= upper_bound)]
    return data

def plot_and_save_graph(data_scaled, input_data_scaled, indices, filename='nearest_countries.png'):
    plt.figure(figsize=(8, 6))

    # 모든 국가 데이터 점으로 표시
    plt.scatter(data_scaled[:, 0], data_scaled[:, 1], label='All Countries', alpha=0.5)

    # 입력값 표시
    plt.scatter(input_data_scaled[:, 0], input_data_scaled[:, 1], color='red', label='Input Data', marker='x')

    # 가장 가까운 국가 3개 표시
    for i in range(3):
        plt.scatter(data_scaled[indices[0][i], 0], data_scaled[indices[0][i], 1], label=f'Nearest Country {i+1}', marker='o')

    # 그래프 레이블과 범례 추가
    plt.xlabel('Total Expense (Scaled)')
    plt.ylabel('Average Day of Staying (Scaled)')
    plt.title('Nearest Countries to Input Data')
    plt.legend()

    # 그래프 저장
    plt.savefig(filename)

    # 그래프 표시
    plt.show()



# 데이터 전처리 및 모델 초기화
class KNNModel:
    def __init__(self, data_path):
        # 데이터 파일 불러오기
        self.data = pd.read_csv(data_path)

        # 데이터에서 #N/A를 NaN으로 대체,결측치가 있는 행 제거
        self.data = self.data.replace('#N/A', np.nan).dropna()

        # 이상치 제거
        self.data = remove_outliers_iqr(self.data, 'total-expense')
        self.data = remove_outliers_iqr(self.data, 'average-day-of-staying')

        # Standard Scaler
        self.scaler = StandardScaler()

        # KNN
        self.model = NearestNeighbors(n_neighbors=3)

        # 스케일링 및 피팅 과정
        self.data_scaled = self.scaler.fit_transform(self.data[['total-expense', 'average-day-of-staying']])
        self.model.fit(self.data_scaled)

    def predict(self, total_expense, average_days):
        # 입력 데이터를 DataFrame으로 변환
        input_data_df = pd.DataFrame([[total_expense, average_days]], columns=['total-expense', 'average-day-of-staying'])
        
        # 입력 데이터 스케일링 및 예측
        input_data_scaled = self.scaler.transform(input_data_df)
        distances, indices = self.model.kneighbors(input_data_scaled)

        # 그래프 그리기 및 저장
        plot_and_save_graph(self.data_scaled, input_data_scaled, indices)
        
        return self.data.iloc[indices[0]]





