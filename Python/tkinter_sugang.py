from tkinter import *
from tkinter import messagebox
import random

global msg
msg = ['성공', '실패']

global L
L = len(msg)

def msgbox():
    # global msg
    global L
    random_msg = random.choice(msg)
    if L > 0:
        idx = random.randint(0, L - 1)
        messagebox.showinfo('확인', random_msg)

root = Tk()
root.geometry('200x100')
bnt = Button(root, text='랜덤', command=msgbox)
bnt.pack()
root.mainloop()