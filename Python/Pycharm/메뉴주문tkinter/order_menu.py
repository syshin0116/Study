import tkinter as tk


class OrderSystem:
    def __init__(self, root):
        self.root = root
        self.root.title("음료 주문")

        self.menu = {
            'coffee': 1500,
            'latte': 2000,
            'smoothie': 3000,
            'tea': 800
        }
        self.order_list = []

        self.create_widgets()

    def create_widgets(self):
        # 주문 버튼 생성
        buttons = ['coffee', 'latte', 'smoothie', 'tea', 'exit']
        for idx, button_text in enumerate(buttons):
            button = tk.Button(self.root, text=button_text, width=10,
                               command=lambda text=button_text: self.order_drink(text))
            button.grid(row=idx, column=0, padx=10, pady=5)

        # 총 금액 텍스트
        self.total_label = tk.Label(self.root, text="금액: 0원", fg="blue", font=("Arial", 14))
        self.total_label.grid(row=len(buttons), column=0, pady=10)

        # 주문 내역 텍스트 박스
        self.order_text = tk.Text(self.root, height=10, width=50)
        self.order_text.grid(row=len(buttons) + 1, column=0, padx=10, pady=10)

        # 창 크기에 맞게 조절
        self.root.grid_rowconfigure(len(buttons) + 1, weight=1)
        self.root.grid_columnconfigure(0, weight=1)

    def order_drink(self, drink):
        if drink == 'exit':
            self.root.quit()
        else:
            if drink in self.menu:
                self.order_list.append(drink)
                self.order_text.insert(tk.END, f"{drink} ")
                self.calculate_total()
            else:
                self.order_text.insert(tk.END, "주문할 수 없는 음료입니다. 다시 입력해주세요.\n")

    def calculate_total(self):
        total_price = sum(self.menu[drink] for drink in self.order_list)
        self.total_label.config(text=f"금액: {total_price}원")


root = tk.Tk()
order_system = OrderSystem(root)
root.geometry('400x400')
root.mainloop()
