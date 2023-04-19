from tkinter import *
import random
import os

class MainWindow():
    def __init__(self, root):

        # list of images located in each categorical directories
        self.img_korean = [PhotoImage(file='./food/korean/'+i) for i in os.listdir('./food/korean')]
        self.img_western = [PhotoImage(file='./food/western/'+i) for i in os.listdir('./food/western')]
        self.img_chinese = [PhotoImage(file='./food/chinese/'+i) for i in os.listdir('./food/chinese')]
        self.img_japanese = [PhotoImage(file='./food/japanese/'+i) for i in os.listdir('./food/japanese')]
        self.img_bunsik = [PhotoImage(file='./food/bunsik/'+i) for i in os.listdir('./food/bunsik')]

        # config for tk window
        root.title('메뉴추천기')
        root.config(padx=10, pady=10, bg='lightblue')

        # canvas
        self.canvas = Canvas(root, height=680, width=680, bg='ivory')
        self.canvas.pack()

        # initial image on canvas
        self.img_main = PhotoImage(file='./food/배고파.png')
        self.img_on_canvas = self.canvas.create_image(340, 340, image=self.img_main)
        self.canvas.create_text(340, 60, text='오늘 뭐 먹지?', fill='brown', font=('나눔바른펜', 30, 'bold'))

        # buttons
        btn1 = Button(root, text='한식', padx=20, font=('나눔바른펜', 20, 'bold'), command=lambda: self.update_img(self.img_korean))
        btn1.pack()
        btn2 = Button(root, text='양식', padx=20, font=('나눔바른펜', 20, 'bold'), command=lambda: self.update_img(self.img_western))
        btn2.pack()
        btn3 = Button(root, text='중식', padx=20, font=('나눔바른펜', 20, 'bold'), command=lambda: self.update_img(self.img_chinese))
        btn3.pack()
        btn4 = Button(root, text='일식', padx=20, font=('나눔바른펜', 20, 'bold'), command=lambda: self.update_img(self.img_japanese))
        btn4.pack()
        btn5 = Button(root, text='분식', padx=20, font=('나눔바른펜', 20, 'bold'), command=lambda: self.update_img(self.img_bunsik))
        btn5.pack()

        btn1.place(x=20, y=600)
        btn2.place(x=150, y=600)
        btn3.place(x=280, y=600)
        btn4.place(x=410, y=600)
        btn5.place(x=540, y=600)

    # update img on click
    def update_img(self, img_list):
        img = random.choice(img_list)
        self.canvas.itemconfig(self.img_on_canvas, image=img)

# run
root = Tk()
MainWindow(root)
root.mainloop()