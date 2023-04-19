T = int(input())
print("T>>", T)
for _ in range(T):
    if _ > 0 and _ != T:
        a = input()

    li = [list(map(int, input().split())) for i in range(9)]
    print("li>>", li)
    answer = 362880  # 각 구간의 곱은 항상 362880여야 한다.
    total = 0
    for i in range(9):
        mul_r = 1  # 가로
        mul_c = 1  # 세로
        mul_sq = 1  # 3x3

        for j in range(9):
            mul_r *= li[i][j]
            mul_c *= li[j][i]

            mul_sq = 1
            if i % 3 == 0 and j % 3 == 0:
                for o in range(3):
                    for k in range(3):
                        mul_sq *= li[i + o][j + k]
                else:
                    if mul_sq == answer:
                        total += 1

        if mul_r == answer:
            total += 1
        if mul_c == answer:
            total += 1

    if total == 27:
        print(f"Case {_ + 1}: CORRECT")
    else:
        print(f"Case {_ + 1}: INCORRECT")