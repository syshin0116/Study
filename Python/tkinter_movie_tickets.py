from tkinter import *  # 사용 라이브러리 load
from tkinter import messagebox

global movie_list  # 글로벌 변수 선언
movie_list = []  # 글로벌 변수 list로 선언

global member
member_list = []

global time
time_list = []

# 1번 영화 선택 함수
def movie1():
    root.title('1번 영화')  # movie_list 변수안에 내용 중 가장 귀에 추가
    msgbox = messagebox.askquestion('1번영화', '예매하시겠습니까?')
    if msgbox == 'yes':
        movie_list.append('1번 영화')
        msgbox = messagebox.showinfo('확인', '예매되었습니다.')
        msgbox1 = messagebox.showinfo('안내', '영화 시작 10분 전 미리 입장해 주세요.')
    else:
        msgbox = messagebox.showinfo('확인', '예매가 취소되었습니다.')


# 2번 영화 선택 함수
def movie2():
    root.title('2번 영화')  # movie_list 변수안에 내용 중 가장 귀에 추가
    msgbox = messagebox.askquestion('2번영화', '예매하시겠습니까?')
    if msgbox == 'yes':
        movie_list.append('2번 영화')
        msgbox = messagebox.showinfo('확인', '예매되었습니다.')
        msgbox1 = messagebox.showinfo('안내', '영화 시작 10분 전 미리 입장해 주세요.')
    else:
        msgbox = messagebox.showinfo('확인', '예매가 취소되었습니다.')


# 3번 영화 선택 함수
def movie3():
    root.title('3번 영화')  # movie_list 변수안에 내용 중 가장 귀에 추가
    msgbox = messagebox.askquestion('3번영화', '예매하시겠습니까?')
    if msgbox == 'yes':
        movie_list.append('3번 영화')
        msgbox = messagebox.showinfo('확인', '예매되었습니다.')
        msgbox1 = messagebox.showinfo('안내', '영화 시작 10분 전 미리 입장해 주세요.')
    else:
        msgbox = messagebox.showinfo('확인', '예매가 취소되었습니다.')


# 선택한 영화 목록 전시 함수
def check_movie():
    root = Tk()
    root.geometry('200x200')
    root.title('영화 예매 목록')

    label3 = Label(root, text='\n'.join(movie_list))
    label3.pack()

    label4 = Label(root, text=''.join(time_list)+'시'+' '+''.join(member_list)+'명')
    label4.pack()

    bnt = Button(root, text='예약 취소', command=cancel)
    bnt.pack()


# 선택한 영화 목록 삭제 함수
def cancel():
    msgbox = messagebox.showinfo('안내', '예매 목록이 삭제되었습니다. 확인하시려면 창을 다시 실행시켜 주세요.')
    movie_list.clear()


# 프로그램 종료 함수
def disconnect():
    msgbox = messagebox.askquestion('확인', '종료하시겠습니까?')
    if msgbox == 'yes':
        msgbox1 = messagebox.showinfo('안내', '종료되었습니다.')
        quit()


# 메인 메뉴 전시 함수
def movies():
    msgbox = messagebox.showinfo('환영 ', '환영합니다 고객님 *^^*')
    root = Tk()
    root.title('영화 예매 프로그램')
    root.geometry('200x300')

    bnt1 = Button(root, text='1번 영화', command=movie1)
    bnt1.pack()

    bnt2 = Button(root, text='2번 영화', command=movie2)
    bnt2.pack()

    bnt3 = Button(root, text='3번 영화', command=movie3)
    bnt3.pack()

    bnt4 = Button(root, text='예매 목록 확인하기', command=check_movie)
    bnt4.pack()

    bnt5 = Button(root, text='종료하기', command=disconnect)
    bnt5.pack()


# ID(인원 수), PW(시간) 입력 함수(로그인 함수 활용)
def check_IDPW():
    get_member_save = user_member.get()
    get_time_save = user_time.get()
    if get_member_save in ID:
        messagebox.showerror('경고', '인원수를 입력하세요')
    elif get_time_save in PW:
        messagebox.showerror('경고', '시간을 입력하세요')
    else:
        member_list.append(get_member_save)
        time_list.append(get_time_save)
        movies()


root = Tk()
root.title('영화 예매 프로그램')
root.resizable(FALSE, FALSE)
root.geometry('500x200')

ID = {''}
PW = ['']
lb1 = Label(root, text='인원수를 입력하세요')
user_member = Entry(root, width=30)

lb2 = Label(root, text='10시~22시 사이의 시간을 입력하세요')
user_time = Entry(root, width=30, )

bt = Button(root, text='다음', command=check_IDPW, )

lb1.grid(row=0, column=0)
user_member.grid(row=0, column=1)
lb2.grid(row=1, column=0)
user_time.grid(row=1, column=1)
bt.grid(row=2, column=1)

root.mainloop()