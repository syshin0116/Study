def check(a):
    for i in range(9):
        row = [0] * 10
        col = [0] * 10
        for j in range(9):
            row[a[i][j]] = 1
            col[a[i][j]] = 1
        if sum(row) != 9 or sum(col) != 9:
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
n = int(sys.stdin.readline())
data = [sys.stdin.readline().strip() for i in range(n)]
print(n)
print(data)

for i in range(1, n + 1):
    a = [list(map(int, data.split())) for _ in range(9)]
    print(a)
    if check(a):
        print(f"Case {i}: CORRECT")
    else:
        print(f"Case {i}: INCORRECT")