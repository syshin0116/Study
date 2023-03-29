import random

w = ['cat', 'banana', 'frog', 'lion', 'fox', 'wolf', 'mouse', 'pengsu']
count = 0
num_correct = 0
input("타자게임 시작 (엔터 입력)")
q = random.choice(w)


while True:
    print("(종료 0):", q)
    x = input()
    if x == '0':
        if count == 0:
            print("입력이 없습니다")
        else:
            print("맞은개수:", num_correct, f"정답률:{num_correct / count * 100:0.2f}")
        break
    count += 1
    if x == q:
        print("맞음!!")
        num_correct += 1
        q = random.choice(w)
    else:
        print("오타! 다시 도전!")
