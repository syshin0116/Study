def check(a):
    for i in range(9):
        row_sum = 1
        col_sum = 1
        for j in range(9):
            row_sum*=a[i][j]
            col_sum*=a[j][i]
        if row_sum != 362880 or col_sum != 362880:
            return False
        for i in range(3):
            for j in range(3):
                row_col = [0] * 10
                for k in range(3):
                    for l in range(3):
                        row_col[a[3*i+k][3*j+l]] = 1
                    if sum(row_col) != 9:
                        return False
                    return True

import sys
n = int(input())

for i in range(n):
    # if sys.stdin.readline().rstrip().split()!=[]:
    #     a = [list(map(int, sys.stdin.readline().rstrip().split())) for i in range(0,9)]
    # else:
    #     a = [list(map(int, sys.stdin.readline().rstrip().split())) for i in range(1,10)]
    if i > 0 and i != n:
        b = input()
    a = [list(map(int, sys.stdin.readline().rstrip().split())) for i in range(9)]
    print(a)
    if check(a):
        print(f"Case {i}: CORRECT")
    else:
        print(f"Case {i}: INCORRECT")