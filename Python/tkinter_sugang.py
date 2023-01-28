from tkinter import *
from tkinter import messagebox
import random

global msg
msg = ['성공', '실패']

student_name_list = ['홍길동']
student_birth_list = ['000101']
global L
L = len(msg)

def msgbox():
    # global msg
    global L
    random_msg = random.choice(msg)
    if L > 0:
        idx = random.randint(0, L - 1)
        messagebox.showinfo('확인', random_msg)

def receive_application():
    get_member_save = user_name.get()
    get_birth_save = user_birth.get()
    if get_member_save not in student_name_list:
        messagebox.showerror('경고', '존재하지 않는 학생 이름입니다.')
    elif get_birth_save not in student_birth_list:
        messagebox.showerror('경고', '존재하지 않는 생년월일입니다')
    else:
        select_course()

# 메인 메뉴 전시 함수
def select_course():
    msgbox = messagebox.showinfo('안내', '수강신청 페이지입니다')
    root = Tk()
    root.title('수강 신청화면')
    root.geometry('500x500')
    course_list = ['수업1', '수업2', '수업3']
    for i, course in enumerate(course_list):
        course_btn = Button(root, text=course, command=lambda :apply_course(course))
        course_btn.pack


def apply_course(course):
    print("선택된 강의:", course)
root = Tk()
root.title('로그인')
root.geometry('500x200')
lb1 = Label(root, text='이름:')
user_name = Entry(root, width=30)

lb2 = Label(root, text='생년월일 6자리 ex)000101')
user_birth = Entry(root, width=30)

bt = Button(root, text='로그인', command=receive_application)
lb1.grid(row=0, column=0)
user_name.grid(row=0, column=1)
lb2.grid(row=1, column=0)
user_birth.grid(row=1, column=1)
bt.grid(row=2, column=1)
root.mainloop()