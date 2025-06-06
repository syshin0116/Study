#!/usr/bin/env python
# -*- coding: utf-8 -*-

import tkinter as tk
from tkinter import ttk, messagebox, simpledialog
from vending_machine import VendingMachine

class VendingMachineGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("음료 자판기")
        self.root.geometry("800x600")
        self.root.resizable(False, False)
        
        # 자판기 객체 생성
        self.vending_machine = VendingMachine()
        
        # 전체 프레임 설정
        self.main_frame = ttk.Frame(self.root, padding=10)
        self.main_frame.pack(fill=tk.BOTH, expand=True)
        
        # 음료 선택 영역 (왼쪽)
        self.drink_frame = ttk.LabelFrame(self.main_frame, text="음료 메뉴", padding=10)
        self.drink_frame.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=5)
        
        # 음료 버튼을 담을 프레임
        self.drinks_buttons_frame = ttk.Frame(self.drink_frame)
        self.drinks_buttons_frame.pack(fill=tk.BOTH, expand=True)
        
        # 제어 패널 영역 (오른쪽)
        self.control_frame = ttk.Frame(self.main_frame, padding=10)
        self.control_frame.pack(side=tk.RIGHT, fill=tk.BOTH, expand=False, padx=5, pady=5)
        
        # 돈 투입 영역
        self.money_frame = ttk.LabelFrame(self.control_frame, text="돈 투입", padding=10)
        self.money_frame.pack(fill=tk.X, pady=10)
        
        # 현재 투입 금액 표시
        self.money_label = ttk.Label(self.money_frame, text="투입 금액: 0원", font=("Arial", 12))
        self.money_label.pack(pady=5)
        
        # 동전 버튼 프레임
        self.coin_buttons_frame = ttk.Frame(self.money_frame)
        self.coin_buttons_frame.pack(fill=tk.X, pady=5)
        
        # 동전 버튼 생성
        self.create_coin_buttons()
        
        # 거래 정보 영역
        self.info_frame = ttk.LabelFrame(self.control_frame, text="거래 정보", padding=10)
        self.info_frame.pack(fill=tk.X, pady=10)
        
        # 선택한 음료 정보
        self.selected_label = ttk.Label(self.info_frame, text="선택한 음료: 없음", font=("Arial", 10))
        self.selected_label.pack(anchor=tk.W)
        
        self.price_label = ttk.Label(self.info_frame, text="가격: 0원", font=("Arial", 10))
        self.price_label.pack(anchor=tk.W)
        
        # 구매/취소 버튼
        self.purchase_button = ttk.Button(self.info_frame, text="구매하기", command=self.purchase_drink)
        self.purchase_button.pack(fill=tk.X, pady=5)
        
        self.cancel_button = ttk.Button(self.info_frame, text="취소하기", command=self.cancel_transaction)
        self.cancel_button.pack(fill=tk.X)
        
        # 관리자 모드 버튼
        self.admin_button = ttk.Button(self.control_frame, text="관리자 모드", command=self.admin_login)
        self.admin_button.pack(fill=tk.X, pady=10)
        
        # 음료 버튼 생성
        self.create_drink_buttons()
        
        # 상태 표시줄
        self.status_var = tk.StringVar()
        self.status_var.set("자판기가 준비되었습니다.")
        self.status_bar = ttk.Label(self.root, textvariable=self.status_var, relief=tk.SUNKEN, anchor=tk.W)
        self.status_bar.pack(side=tk.BOTTOM, fill=tk.X)
        
        # 선택한 음료 정보
        self.selected_drink = None
        
        # 투입 금액 초기화
        self.vending_machine.inserted_money = 0
    
    def create_coin_buttons(self):
        """동전 투입 버튼 생성"""
        coin_values = [50, 100, 500, 1000]
        
        for value in coin_values:
            button = ttk.Button(
                self.coin_buttons_frame, 
                text=f"{value}원", 
                command=lambda v=value: self.insert_money(v)
            )
            button.pack(side=tk.LEFT, fill=tk.X, expand=True, padx=2)
    
    def create_drink_buttons(self):
        """음료 선택 버튼 생성"""
        # 기존 버튼 제거
        for widget in self.drinks_buttons_frame.winfo_children():
            widget.destroy()
        
        # 음료 버튼을 그리드로 배치
        row, col = 0, 0
        for idx, (name, info) in enumerate(self.vending_machine.drinks.items(), 1):
            # 버튼 생성
            btn_text = f"{name}\n{info['price']}원\n재고: {info['stock']}"
            button = ttk.Button(
                self.drinks_buttons_frame,
                text=btn_text,
                command=lambda n=name: self.select_drink(n),
                width=15
            )
            
            # 품절이면 비활성화
            if info["stock"] <= 0:
                button.configure(text=f"{name}\n품절", state=tk.DISABLED)
            
            # 그리드에 버튼 배치
            button.grid(row=row, column=col, padx=5, pady=5, sticky=tk.NSEW)
            
            # 다음 위치 계산
            col += 1
            if col > 2:  # 3열로 제한
                col = 0
                row += 1
        
        # 그리드 크기 조정
        for i in range(3):
            self.drinks_buttons_frame.columnconfigure(i, weight=1)
        for i in range((len(self.vending_machine.drinks) // 3) + 1):
            self.drinks_buttons_frame.rowconfigure(i, weight=1)
    
    def insert_money(self, amount):
        """돈 투입 처리"""
        self.vending_machine.inserted_money += amount
        self.money_label.configure(text=f"투입 금액: {self.vending_machine.inserted_money}원")
        self.status_var.set(f"{amount}원이 투입되었습니다. 총 {self.vending_machine.inserted_money}원")
    
    def select_drink(self, drink_name):
        """음료 선택 처리"""
        self.selected_drink = drink_name
        price = self.vending_machine.drinks[drink_name]["price"]
        
        self.selected_label.configure(text=f"선택한 음료: {drink_name}")
        self.price_label.configure(text=f"가격: {price}원")
        
        self.status_var.set(f"{drink_name}을(를) 선택하셨습니다. 가격은 {price}원입니다.")
    
    def purchase_drink(self):
        """음료 구매 처리"""
        if not self.selected_drink:
            messagebox.showwarning("알림", "음료를 먼저 선택해주세요.")
            return
        
        drink_info = self.vending_machine.drinks[self.selected_drink]
        price = drink_info["price"]
        
        # 금액 부족 확인
        if self.vending_machine.inserted_money < price:
            shortage = price - self.vending_machine.inserted_money
            messagebox.showwarning("금액 부족", f"금액이 {shortage}원 부족합니다.")
            return
        
        # 재고 확인
        if drink_info["stock"] <= 0:
            messagebox.showwarning("품절", f"{self.selected_drink}이(가) 품절되었습니다.")
            self.create_drink_buttons()  # 버튼 갱신
            return
        
        # 거스름돈 계산
        change = self.vending_machine.inserted_money - price
        
        # 거스름돈 반환 가능 여부 확인
        if not self.vending_machine.check_change(change):
            messagebox.showerror("오류", "거스름돈이 부족합니다. 관리자에게 문의하세요.")
            return
        
        # 음료 재고 감소
        drink_info["stock"] -= 1
        
        # 동전 수량 업데이트 (투입된 금액 처리)
        self.vending_machine.coins[1000] += self.vending_machine.inserted_money // 1000
        self.vending_machine.inserted_money %= 1000
        self.vending_machine.coins[500] += self.vending_machine.inserted_money // 500
        self.vending_machine.inserted_money %= 500
        self.vending_machine.coins[100] += self.vending_machine.inserted_money // 100
        self.vending_machine.inserted_money %= 100
        self.vending_machine.coins[50] += self.vending_machine.inserted_money // 50
        
        # 거스름돈 계산 및 출력
        change_message = self.format_change(change)
        
        # 거래 완료 메시지
        message = f"{self.selected_drink}이(가) 나왔습니다!\n\n{change_message}"
        messagebox.showinfo("구매 완료", message)
        
        # 상태 초기화
        self.selected_drink = None
        self.vending_machine.inserted_money = 0
        self.money_label.configure(text="투입 금액: 0원")
        self.selected_label.configure(text="선택한 음료: 없음")
        self.price_label.configure(text="가격: 0원")
        self.status_var.set("구매가 완료되었습니다. 이용해 주셔서 감사합니다.")
        
        # 음료 버튼 갱신
        self.create_drink_buttons()
    
    def format_change(self, change):
        """거스름돈 형식 지정"""
        if change == 0:
            return "거스름돈이 없습니다."
        
        # 거스름돈 계산
        change_1000 = min(change // 1000, self.vending_machine.coins[1000])
        change -= change_1000 * 1000
        self.vending_machine.coins[1000] -= change_1000
        
        change_500 = min(change // 500, self.vending_machine.coins[500])
        change -= change_500 * 500
        self.vending_machine.coins[500] -= change_500
        
        change_100 = min(change // 100, self.vending_machine.coins[100])
        change -= change_100 * 100
        self.vending_machine.coins[100] -= change_100
        
        change_50 = min(change // 50, self.vending_machine.coins[50])
        change -= change_50 * 50
        self.vending_machine.coins[50] -= change_50
        
        # 메시지 생성
        message = "거스름돈:\n"
        if change_1000 > 0:
            message += f"1000원: {change_1000}개\n"
        if change_500 > 0:
            message += f"500원: {change_500}개\n"
        if change_100 > 0:
            message += f"100원: {change_100}개\n"
        if change_50 > 0:
            message += f"50원: {change_50}개\n"
        
        return message
    
    def cancel_transaction(self):
        """거래 취소 처리"""
        if self.vending_machine.inserted_money > 0:
            # 거스름돈 계산
            change_message = self.format_change(self.vending_machine.inserted_money)
            messagebox.showinfo("거래 취소", f"거래가 취소되었습니다.\n\n{change_message}")
            
            # 상태 초기화
            self.vending_machine.inserted_money = 0
            self.money_label.configure(text="투입 금액: 0원")
            self.selected_drink = None
            self.selected_label.configure(text="선택한 음료: 없음")
            self.price_label.configure(text="가격: 0원")
            self.status_var.set("거래가 취소되었습니다.")
        else:
            messagebox.showinfo("알림", "취소할 거래가 없습니다.")
    
    def admin_login(self):
        """관리자 로그인 처리"""
        password = simpledialog.askstring("관리자 로그인", "비밀번호를 입력하세요:", show='*')
        
        if password == self.vending_machine.admin_password:
            self.open_admin_window()
        else:
            messagebox.showerror("오류", "비밀번호가 일치하지 않습니다.")
    
    def open_admin_window(self):
        """관리자 창 열기"""
        admin_window = tk.Toplevel(self.root)
        admin_window.title("관리자 모드")
        admin_window.geometry("600x500")
        admin_window.grab_set()  # 모달 창으로 설정
        
        # 탭 생성
        notebook = ttk.Notebook(admin_window)
        notebook.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # 재고 관리 탭
        stock_tab = ttk.Frame(notebook)
        notebook.add(stock_tab, text="재고 관리")
        
        # 음료 재고 목록
        stock_frame = ttk.LabelFrame(stock_tab, text="음료 재고", padding=10)
        stock_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # 음료 목록을 표시할 트리뷰
        columns = ("name", "price", "stock")
        stock_tree = ttk.Treeview(stock_frame, columns=columns, show="headings")
        stock_tree.heading("name", text="음료명")
        stock_tree.heading("price", text="가격 (원)")
        stock_tree.heading("stock", text="재고")
        
        stock_tree.column("name", width=200)
        stock_tree.column("price", width=100)
        stock_tree.column("stock", width=100)
        
        stock_tree.pack(fill=tk.BOTH, expand=True)
        
        # 음료 재고 데이터 추가
        for name, info in self.vending_machine.drinks.items():
            stock_tree.insert("", tk.END, values=(name, info["price"], info["stock"]))
        
        # 재고 관리 버튼 프레임
        stock_btn_frame = ttk.Frame(stock_tab)
        stock_btn_frame.pack(fill=tk.X, pady=10)
        
        # 재고 추가 버튼
        add_stock_btn = ttk.Button(
            stock_btn_frame, 
            text="재고 추가", 
            command=lambda: self.add_stock(stock_tree)
        )
        add_stock_btn.pack(side=tk.LEFT, padx=5)
        
        # 가격 수정 버튼
        change_price_btn = ttk.Button(
            stock_btn_frame, 
            text="가격 수정", 
            command=lambda: self.change_price(stock_tree)
        )
        change_price_btn.pack(side=tk.LEFT, padx=5)
        
        # 동전 관리 탭
        coin_tab = ttk.Frame(notebook)
        notebook.add(coin_tab, text="동전 관리")
        
        # 동전 재고 목록
        coin_frame = ttk.LabelFrame(coin_tab, text="동전 재고", padding=10)
        coin_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # 동전 목록을 표시할 트리뷰
        coin_columns = ("coin", "count")
        coin_tree = ttk.Treeview(coin_frame, columns=coin_columns, show="headings")
        coin_tree.heading("coin", text="동전 종류")
        coin_tree.heading("count", text="개수")
        
        coin_tree.column("coin", width=200)
        coin_tree.column("count", width=200)
        
        coin_tree.pack(fill=tk.BOTH, expand=True)
        
        # 동전 데이터 추가
        coin_names = {1000: "1000원 지폐", 500: "500원 동전", 100: "100원 동전", 50: "50원 동전"}
        for coin, count in self.vending_machine.coins.items():
            coin_tree.insert("", tk.END, values=(coin_names[coin], count))
        
        # 동전 추가 버튼
        add_coin_btn = ttk.Button(
            coin_tab, 
            text="동전 추가", 
            command=lambda: self.add_coins(coin_tree, coin_names)
        )
        add_coin_btn.pack(pady=10)
        
        # 시스템 탭
        system_tab = ttk.Frame(notebook)
        notebook.add(system_tab, text="시스템")
        
        # 비밀번호 변경 프레임
        pwd_frame = ttk.LabelFrame(system_tab, text="비밀번호 변경", padding=10)
        pwd_frame.pack(fill=tk.X, padx=10, pady=10)
        
        # 비밀번호 입력 필드
        pwd_entry = ttk.Entry(pwd_frame, show='*')
        pwd_entry.pack(fill=tk.X, pady=5)
        
        # 비밀번호 변경 버튼
        change_pwd_btn = ttk.Button(
            pwd_frame, 
            text="비밀번호 변경", 
            command=lambda: self.change_password(pwd_entry.get())
        )
        change_pwd_btn.pack(pady=5)
        
        # 초기화 버튼
        reset_btn = ttk.Button(
            system_tab, 
            text="자판기 초기화", 
            command=self.confirm_reset
        )
        reset_btn.pack(pady=10)
        
        # 창 닫기 버튼
        close_btn = ttk.Button(
            admin_window, 
            text="관리자 모드 종료", 
            command=admin_window.destroy
        )
        close_btn.pack(pady=10)
    
    def add_stock(self, tree):
        """재고 추가 처리"""
        selected = tree.selection()
        
        if not selected:
            messagebox.showwarning("알림", "음료를 선택해주세요.")
            return
        
        # 선택한 항목 정보 가져오기
        item = tree.item(selected[0])
        drink_name = item['values'][0]
        
        # 수량 입력 받기
        amount = simpledialog.askinteger("재고 추가", f"{drink_name}의 추가할 수량을 입력하세요:")
        
        if amount is not None and amount > 0:
            # 재고 추가
            self.vending_machine.drinks[drink_name]["stock"] += amount
            
            # 트리뷰 갱신
            tree.item(selected[0], values=(
                drink_name, 
                self.vending_machine.drinks[drink_name]["price"],
                self.vending_machine.drinks[drink_name]["stock"]
            ))
            
            # 음료 버튼 갱신
            self.create_drink_buttons()
            
            messagebox.showinfo("알림", f"{drink_name}의 재고가 {amount}개 추가되었습니다.")
        elif amount is not None:
            messagebox.showwarning("알림", "유효한 수량을 입력하세요.")
    
    def change_price(self, tree):
        """가격 변경 처리"""
        selected = tree.selection()
        
        if not selected:
            messagebox.showwarning("알림", "음료를 선택해주세요.")
            return
        
        # 선택한 항목 정보 가져오기
        item = tree.item(selected[0])
        drink_name = item['values'][0]
        current_price = self.vending_machine.drinks[drink_name]["price"]
        
        # 새 가격 입력 받기
        new_price = simpledialog.askinteger(
            "가격 변경", 
            f"{drink_name}의 새 가격을 입력하세요 (현재: {current_price}원):"
        )
        
        if new_price is not None and new_price > 0:
            # 가격 변경
            self.vending_machine.drinks[drink_name]["price"] = new_price
            
            # 트리뷰 갱신
            tree.item(selected[0], values=(
                drink_name, 
                new_price,
                self.vending_machine.drinks[drink_name]["stock"]
            ))
            
            # 음료 버튼 갱신
            self.create_drink_buttons()
            
            messagebox.showinfo(
                "알림", 
                f"{drink_name}의 가격이 {current_price}원에서 {new_price}원으로 변경되었습니다."
            )
        elif new_price is not None:
            messagebox.showwarning("알림", "유효한 가격을 입력하세요.")
    
    def add_coins(self, tree, coin_names):
        """동전 추가 처리"""
        selected = tree.selection()
        
        if not selected:
            messagebox.showwarning("알림", "동전 종류를 선택해주세요.")
            return
        
        # 선택한 항목 정보 가져오기
        item = tree.item(selected[0])
        coin_name = item['values'][0]
        
        # 동전 이름에서 동전 값 찾기
        coin_value = None
        for value, name in coin_names.items():
            if name == coin_name:
                coin_value = value
                break
        
        if coin_value is None:
            messagebox.showerror("오류", "잘못된 동전 유형입니다.")
            return
        
        # 수량 입력 받기
        amount = simpledialog.askinteger("동전 추가", f"{coin_name}의 추가할 수량을 입력하세요:")
        
        if amount is not None and amount > 0:
            # 동전 추가
            self.vending_machine.coins[coin_value] += amount
            
            # 트리뷰 갱신
            tree.item(selected[0], values=(coin_name, self.vending_machine.coins[coin_value]))
            
            messagebox.showinfo(
                "알림", 
                f"{coin_name}이(가) {amount}개 추가되었습니다. 현재 {self.vending_machine.coins[coin_value]}개가 있습니다."
            )
        elif amount is not None:
            messagebox.showwarning("알림", "유효한 수량을 입력하세요.")
    
    def change_password(self, new_password):
        """비밀번호 변경 처리"""
        if not new_password:
            messagebox.showwarning("알림", "비밀번호를 입력하세요.")
            return
        
        self.vending_machine.admin_password = new_password
        messagebox.showinfo("알림", "비밀번호가 변경되었습니다.")
    
    def confirm_reset(self):
        """자판기 초기화 확인"""
        confirm = messagebox.askyesno("확인", "정말 자판기를 초기화하시겠습니까?")
        
        if confirm:
            # 자판기 초기화
            self.vending_machine.__init__()
            
            # UI 갱신
            self.vending_machine.inserted_money = 0
            self.money_label.configure(text="투입 금액: 0원")
            self.selected_drink = None
            self.selected_label.configure(text="선택한 음료: 없음")
            self.price_label.configure(text="가격: 0원")
            self.create_drink_buttons()
            
            messagebox.showinfo("알림", "자판기가 초기화되었습니다.")
            self.status_var.set("자판기가 초기화되었습니다.")

# 프로그램 실행
if __name__ == "__main__":
    root = tk.Tk()
    app = VendingMachineGUI(root)
    root.mainloop() 