from tkinter import *
from tkinter import messagebox
import os
import random

global lecture_list  # 글로벌 변수 선언
lecture_list = []  # 글로벌 변수 list로 선언

global name_list  # 글로벌 변수 선언
name_list = []  # 글로벌 변수 list로 선언

global birth_list  # 글로벌 변수 선언
birth_list = []  # 글로벌 변수 list로 선언


# 미적분학과 벡터해석 선택 함수
def lecture1():
    root.title('미적분학과 벡터 해석')
    msgbox = messagebox.askquestion('미적분학과 벡터해석', '신청하시겠습니까?')
    if msgbox == 'yes':
        idx = random.randint(0, 1)  # 수강 신청 성공 실패 여부를 랜덤으로 출력하는 함수 / 1이면 성공, 0이면 실패
        if idx:
            lecture_list.append('미적분학과 벡터해석')  # lecture_list 변수 안에 '미적분학과 벡터 해석' 추가
            msgbox = messagebox.showinfo('확인', '신청되었습니다.')
            msgbox1 = messagebox.showinfo('안내', '아래 수강 신청 확인하기 버튼을 클릭하여 확인해 주세요.')
        else:
            msgbox = messagebox.showerror('실패', '실패했습니다.')
    else:
        msgbox = messagebox.showerror('확인', '수강 신청이 취소되었습니다.')


# 일반 생물학 및 실험 선택 함수
def lecture2():
    root.title('일반 생물학 및 실험')  # lecture_list 변수안에 내용 중 가장  뒤에 추가
    msgbox = messagebox.askquestion('일반 생물학 및 실험', '신청하시겠습니까?')
    if msgbox == 'yes':
        idx = random.randint(0, 1)  # 수강 신청 성공 실패 여부를 랜덤으로 출력하는 함수 / 1이면 성공, 0이면 실패
        if idx:
            lecture_list.append('일반 생물학 및 실험')  # lecture_list 변수 안에 '일반 생물학 및 실험 선택' 추가
            msgbox = messagebox.showinfo('확인', '신청되었습니다.')
            msgbox1 = messagebox.showinfo('안내', '아래 수강 신청 확인하기 버튼을 클릭하여 확인해 주세요.')
        else:
            msgbox = messagebox.showerror('실패', '실패했습니다.')
    else:
        msgbox = messagebox.showerror('확인', '수강 신청이 취소되었습니다.')


# 일반 화학 및 실험 선택 함수
def lecture3():
    root.title('일반 화학 및 실험')  # lecture_list 변수안에 내용 중 가장 뒤에 추가
    msgbox = messagebox.askquestion('일반 화학 및 실험', '신청하시겠습니까?')
    if msgbox == 'yes':
        idx = random.randint(0, 1)  # 수강 신청 성공 실패 여부를 랜덤으로 출력하는 함수 / 1이면 성공, 0이면 실패
        if idx:
            lecture_list.append('일반 화학 및 실험')  # lecture_list 변수 안에 '일반 화학 및 실험 선택' 추가
            msgbox = messagebox.showinfo('확인', '신청되었습니다.')
            msgbox1 = messagebox.showinfo('안내', '아래 수강 신청 확인하기 버튼을 클릭하여 확인해 주세요.')
        else:
            msgbox = messagebox.showerror('실패', '실패했습니다.')
    else:
        msgbox = messagebox.showerror('확인', '수강 신청이 취소되었습니다.')


# 일반 물리학 및 실험 선택 함수
def lecture4():
    root.title('일반 물리학 및 실험')  # lecture_list 변수안에 내용 중 가장 뒤에 추가
    msgbox = messagebox.askquestion('일반 물리학 및 실험', '신청하시겠습니까?')
    if msgbox == 'yes':
        idx = random.randint(0, 1)  # 수강 신청 성공 실패 여부를 랜덤으로 출력하는 함수 / 1이면 성공, 0이면 실패
        if idx:
            lecture_list.append('일반 물리학 및 실험')  # lecture_list 변수 안에 '일반 물리학 및 실험 선택' 추가
            msgbox = messagebox.showinfo('확인', '신청되었습니다.')
            msgbox1 = messagebox.showinfo('안내', '아래 수강 신청 확인하기 버튼을 클릭하여 확인해 주세요.')
        else:
            msgbox = messagebox.showerror('실패', '실패했습니다.')
    else:
        msgbox = messagebox.showerror('확인', '수강 신청이 취소되었습니다.')


# 선택한 수강 목록 확인 함수
def check_lecture():
    # root 사용시 중복 위험이 있어 check를 사용함
    # 다른 변수에서 여기에 접근할 수 있도록 check를 global 변수로 지정함
    global check
    check = Tk()
    check.geometry('200x200')
    check.title('수강 신청 확인하기')

    label4 = Label(check, text=''.join(name_list) + '님의 수강 신청 목록')  # label4가 제일 위에 출력되도록 설정
    label4.pack()

    label3 = Label(check, text='\n'.join(lecture_list))  # lecture_list에 저장된 값을 출력 / 예)일반 물리학 및 실험 등등
    label3.pack()

    bnt = Button(check, text='전체 수강 취소', command=cancel)
    bnt.pack()


# 선택한 영화 목록 삭제 함수
def cancel():
    global check
    msgbox = messagebox.askquestion('확인', '수강 신청을 취소하시겠습니까?')
    if msgbox == 'yes':
        lecture_list.clear()
        msgbox = messagebox.showinfo('안내', '수강 신청이 취소되었습니다. 확인하시려면 창을 다시 실행시켜 주세요.')
        check.withdraw()  # check창을 닫는 명령어


# 프로그램 종료 함수
def disconnect():
    msgbox = messagebox.askquestion('확인', '종료하시겠습니까?')
    if msgbox == 'yes':
        msgbox1 = messagebox.showinfo('안내', '종료되었습니다.')
        os._exit(0)  # 프로그램 전체를 다 종료하는 함수


# 메인 메뉴 전시 함수
def lecture():
    msgbox = messagebox.showinfo('안내', '수강 신청 기간은 2월 23일(목) 9:00~17:00까지입니다.')
    root = Tk()
    root.title('수강 신청 프로그램')
    root.geometry('500x300')

    bnt1 = Button(root, text='미적분학과 벡터해석', command=lecture1)
    bnt1.pack()

    bnt2 = Button(root, text='일반 생물학 및 실험', command=lecture2)
    bnt2.pack()

    bnt3 = Button(root, text='일반 화학 및 실험', command=lecture3)
    bnt3.pack()

    bnt4 = Button(root, text='일반 물리학 및 실험', command=lecture4)
    bnt4.pack()

    bnt5 = Button(root, text='수강 신청 확인하기', command=check_lecture)
    bnt5.pack()

    bnt6 = Button(root, text='종료하기', command=disconnect)
    bnt6.pack()


# ID(이름), PW(생년월일) 입력 함수(로그인 함수 활용)
def check_IDPW():
    get_name_save = user_name.get()
    get_birth_save = user_birth.get()
    if get_name_save == '':
        messagebox.showerror('경고', '이름을 입력하세요.')
    elif len(get_birth_save) != 6:  # 입력된 생년월일의 길이가 6자리가 되도록 설정
        messagebox.showerror('경고', '생년월일을 6자리로 입력하세요.')  # 만약 입력된 생년월일이 6자리가 아닐 경우 에러 메세지가 출력되도록 설정
    else:
        name_list.append(get_name_save)
        birth_list.append(get_birth_save)
        root.withdraw()
        lecture()


# 클래스룸에 올라온 11주차 ppt 17페이지 로그인창 변형 함수 활용
root = Tk()
root.title('수강 신청 프로그램')
root.resizable(FALSE, FALSE)
root.geometry('500x200')

ID = {''}
PW = ['']
lb1 = Label(root, text='이름을 입력하세요')
user_name = Entry(root, width=30)

lb2 = Label(root, text='생년월일을 6자리로 입력하세요 예)010101')
user_birth = Entry(root, width=30, )

bt = Button(root, text='확인', command=check_IDPW, )

lb1.grid(row=0, column=0)
user_name.grid(row=0, column=1)
lb2.grid(row=1, column=0)
user_birth.grid(row=1, column=1)
bt.grid(row=2, column=1)

root.mainloop()