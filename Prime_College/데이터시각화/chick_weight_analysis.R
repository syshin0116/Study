# 문제1: ChickWeight 데이터셋을 활용한 시각화
# 필요한 라이브러리 불러오기
library(ggplot2)

# ChickWeight 데이터셋 불러오기
data(ChickWeight)

# 데이터 구조 확인
str(ChickWeight)
head(ChickWeight)

# 1. Time을 x축, weight를 y축으로 하여 선 그래프 그리기 (group = Chick)
p1 <- ggplot(ChickWeight, aes(x = Time, y = weight, group = Chick)) +
  geom_line() +
  labs(title = "병아리 별 체중 변화",
       x = "시간 (일)", 
       y = "체중 (g)") +
  theme_minimal()

print(p1)

# 2. Diet에 따라 선의 색상을 다르게 지정하고 제목과 축 레이블 추가
p2 <- ggplot(ChickWeight, aes(x = Time, y = weight, group = Chick, color = Diet)) +
  geom_line() +
  labs(title = "Diet에 따른 병아리 체중 변화",
       x = "시간 (일)", 
       y = "체중 (g)",
       color = "식이요법") +
  theme_minimal()

print(p2)

# 3 & 4. Diet 별 평균 체중 변화를 추가하여 패턴 비교
p3 <- ggplot() +
  # 개별 병아리 성장 곡선 (투명도 낮게)
  geom_line(data = ChickWeight, 
            aes(x = Time, y = weight, group = Chick, color = Diet),
            alpha = 0.3) +
  # Diet 별 평균 체중 변화 (굵은 선)
  stat_summary(data = ChickWeight,
               aes(x = Time, y = weight, color = Diet, group = Diet),
               fun = mean, geom = "line", size = 1.5) +
  labs(title = "Diet에 따른 병아리 체중 변화 패턴 비교",
       subtitle = "개별 곡선 및 Diet 별 평균",
       x = "시간 (일)", 
       y = "체중 (g)",
       color = "식이요법") +
  theme_minimal()

print(p3)

# Diet별 최종 체중 분포 (Time=21) 비교
final_weights <- subset(ChickWeight, Time == 21)
p4 <- ggplot(final_weights, aes(x = Diet, y = weight, fill = Diet)) +
  geom_boxplot() +
  labs(title = "21일 후 Diet별 최종 체중 분포",
       x = "식이요법", 
       y = "체중 (g)") +
  theme_minimal()

print(p4)

# 결과 해석:
# 
# ChickWeight 데이터셋을 분석한 결과, 다양한 식이요법(Diet)이 병아리의 성장 패턴에 뚜렷한 영향을 미치는 것으로 나타났다. 시각화 결과를 살펴보면, Diet 1을 섭취한 병아리들은 가장 느린 성장률을 보이며 최종 체중이 가장 낮았다. Diet 2는 Diet 1보다 상대적으로 빠른 성장률을 보여주었으며, 중간 정도의 최종 체중에 도달했다. Diet 3은 중상위 성장률을 나타내며 Diet 2보다 높은 최종 체중을 기록했다. 특히 주목할 만한 것은 Diet 4를 섭취한 병아리들로, 이들은 가장 빠른 성장률과 가장 높은 최종 체중을 달성했다.
# 
# 이러한 성장 패턴의 차이는 각 식이요법의 영양학적 구성 차이에서 비롯된 것으로 추정된다. Diet 4는 병아리 성장에 가장 적합한 영양소 조합을 제공했을 가능성이 높다. 특히 단백질, 지방, 탄수화물, 비타민, 미네랄 등의 영양소 비율이 성장에 중요한 영향을 미친 것으로 판단된다. Diet 4는 실험 초기 단계부터 다른 식이요법과 차이를 보이는 것으로 보아, 성장 초기에 필요한 필수 영양소가 더 풍부하게 포함되었을 것으로 추론할 수 있다.
# 
# 또한 같은 식이요법 내에서도 개체별로 성장 패턴에 차이가 나타나는데, 이는 유전적 요인이나 병아리들의 초기 상태 차이에서 비롯되었을 가능성이 있다. 각 개체는 동일한 식이 조건에서도 영양소 흡수율이나 대사 효율성에 차이가 있을 수 있기 때문이다. 결론적으로, 본 분석을 통해 식이요법의 종류가 병아리의 체중 증가와 성장 패턴에 결정적인 영향을 미치며, 특히 Diet 4가 성장 촉진에 가장 효과적임을 확인할 수 있었다. 