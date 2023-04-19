import turtle

# 전역 선언 부분
width, height = 1000, 300
curX, curY = 0, 0

# 메인 코드 부분
if __name__ == "__main__":
    turtle.title('거북이로 2진수 표현하기')
    turtle.shape('turtle')
    turtle.setup(width + 50, height + 50)
    turtle.screensize(width, height)
    turtle.penup()
    turtle.left(90)

    # 첫 번째 숫자 입력받기
    num1 = int(input("첫 번째 숫자를 입력하세요: "))
    이진수1 = bin(num1)[2:]
    print(f"{num1}의 2진수: {이진수1}")
    curX = width / 2
    for i in range(len(이진수1)):
        turtle.goto(curX, curY)
        if 이진수1[i] == '1':
            turtle.color('red')
            turtle.turtlesize(2)
        else:
            turtle.color('blue')
            turtle.turtlesize(1)
        turtle.stamp()
        curX -= 50

    # 두 번째 숫자 입력받기
    num2 = int(input("두 번째 숫자를 입력하세요: "))
    이진수2 = bin(num2)[2:]
    print(f"{num2}의 2진수: {이진수2}")
    curX = width / 2
    curY -= 100
    for i in range(len(이진수2)):
        turtle.goto(curX, curY)
        if 이진수2[i] == '1':
            turtle.color('red')
            turtle.turtlesize(2)
        else:
            turtle.color('blue')
            turtle.turtlesize(1)
        turtle.stamp()
        curX -= 50

    # 두 숫자의 비트 논리곱 구하기
    bitwise_and = num1 & num2
    이진수_and = bin(bitwise_and)[2:]
    print(f"{num1}과 {num2}의 비트 논리곱: {이진수_and}")
    curX = width / 2
    curY -= 100
    for i in range(len(이진수_and)):
        turtle.goto(curX, curY)
        if 이진수_and[i] == '1':
            turtle.color('red')
            turtle.turtlesize(2)
        else:
            turtle.color('blue')
            turtle.turtlesize(1)
        turtle.stamp()
        curX -= 50

    turtle.penup()
    turtle.done()