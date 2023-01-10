scores = []
while len(scores)<5:
    score = int(input('score input: '))
    if (-1 >= score or 101 <= score):
        print("유효한 점수가 아닙니다.")
        continue
    scores.append(score)

avg = sum(scores) / len(scores)

print('합계', sum(scores))
print('평균', avg)