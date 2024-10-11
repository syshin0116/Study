from tkinter import *
from tkinter import messagebox


def create_second_window():
    window_second = Toplevel(window_first)
    window_second.title("새롭게 만들어진 이미지 윈도우 창")
    window_second.geometry("320x320")

    image_7 = Label(window_second, image=photos[6])
    image_7.pack()

    def confirm_quit():
        if messagebox.askokcancel(
            "Kill?", "Your program is still running!\n Do you want to kill it?"
        ):
            window_first.quit()

    button_second = Button(window_second, text="종료하기", command=confirm_quit)
    button_second.pack()


def create_third_window():
    window_third = Toplevel(window_first)
    window_third.title("새롭게 만들어진 메뉴 윈도우 창")
    window_third.geometry("320x320")

    image_8 = Label(window_third, image=photos[7])
    image_8.pack()

    def confirm_quit():
        if messagebox.askokcancel(
            "Kill?", "Your program is still running!\n Do you want to kill it?"
        ):
            window_first.quit()

    menubar = Menu(window_third)
    menu_1 = Menu(menubar, tearoff=0)
    menu_1.add_command(label="하위 메뉴 1-1")
    menu_1.add_command(label="하위 메뉴 1-2")
    menu_1.add_command(label="하위 메뉴 1-3", command=confirm_quit)
    menubar.add_cascade(label="상위 메뉴 1", menu=menu_1)
    window_third.config(menu=menubar)


window_first = Tk()
window_first.title("안드로이드")
window_first.geometry("540x360")

photos = [PhotoImage(file=f"android0{i+1}.gif") for i in range(8)]

buttons = []
for i in range(6):
    row = i // 3
    col = i % 3
    button = Button(window_first, image=photos[i])
    button.place(x=col * 180, y=row * 180)
    buttons.append(button)

buttons[1].config(command=create_second_window)
buttons[4].config(command=create_third_window)

window_first.mainloop()
