import pandas as pd
import matplotlib.pyplot as plt
import warnings
warnings.filterwarnings(action='ignore')
#차트글꼴
plt.rc('font', family='Malgun Gothic')
child_abuse = pd.DataFrame({
    'occurance':[10027, 17150, 18700, 22367, 24604, 30070],
    'years':[2014, 2015, 2016, 2017, 2018, 2019],
    'death':[14, 16, 36, 38, 28, 43]
})
child_abuse.to_csv("child_abuse.csv", index=False)
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12, 6))
ax1.bar(child_abuse.years, child_abuse.occurance)
ax1.set_title('아동학대 사례 건수')
ax1.set_xlabel('년도')
ax1.set_ylabel('건수')

ax2.bar(child_abuse.years, child_abuse.death)
ax2.set_title('학대로 인한 아동사망')
ax2.set_xlabel('년도')
ax2.set_ylabel('사망수')

fig.suptitle('통계로 본 아동학대 실태')

plt.show()