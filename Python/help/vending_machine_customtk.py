#!/usr/bin/env python
# -*- coding: utf-8 -*-

import customtkinter as ctk
from tkinter import messagebox, simpledialog
from vending_machine import VendingMachine

# Set appearance mode and default color theme
ctk.set_appearance_mode("System")  # "System", "Dark", "Light"
ctk.set_default_color_theme("blue")  # "blue", "green", "dark-blue"

class VendingMachineGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("음료 자판기")
        self.root.geometry("1000x700")
        self.root.resizable(True, True)
        
        # 자판기 객체 생성
        self.vending_machine = VendingMachine()
        
        # 색상 캐싱을 위한 딕셔너리 추가 (여기로 이동)
        self.color_cache = {}
        
        # 자주 사용되는 폰트 캐싱 (성능 향상)
        self.fonts = {
            "title": ctk.CTkFont(size=24, weight="bold"),
            "subtitle": ctk.CTkFont(size=18, weight="bold"),
            "regular": ctk.CTkFont(size=14),
            "small": ctk.CTkFont(size=12),
            "button": ctk.CTkFont(size=14),
            "small_bold": ctk.CTkFont(size=12, weight="bold"),
            "tiny": ctk.CTkFont(size=10)
        }
        
        # 전체 프레임 설정
        self.main_frame = ctk.CTkFrame(self.root)
        self.main_frame.pack(fill="both", expand=True, padx=20, pady=20)
        
        # 상단 제목
        self.title_label = ctk.CTkLabel(
            self.main_frame, 
            text="음료 자판기", 
            font=self.fonts["title"]
        )
        self.title_label.pack(pady=(0, 20))
        
        # 콘텐츠 프레임 (음료 선택 및 제어 패널)
        self.content_frame = ctk.CTkFrame(self.main_frame)
        self.content_frame.pack(fill="both", expand=True)
        
        # 음료 선택 영역 (왼쪽)
        self.drink_frame = ctk.CTkFrame(self.content_frame)
        self.drink_frame.pack(side="left", fill="both", expand=True, padx=(0, 10))
        
        # 음료 선택 레이블
        self.drink_label = ctk.CTkLabel(
            self.drink_frame, 
            text="음료 메뉴", 
            font=self.fonts["subtitle"]
        )
        self.drink_label.pack(pady=(10, 5))
        
        # 음료 버튼을 담을 스크롤 프레임
        self.drinks_scroll = ctk.CTkScrollableFrame(self.drink_frame)
        self.drinks_scroll.pack(fill="both", expand=True, padx=10, pady=10)
        
        # 음료 버튼을 담을 그리드 프레임
        self.drinks_buttons_frame = ctk.CTkFrame(self.drinks_scroll, fg_color="transparent")
        self.drinks_buttons_frame.pack(fill="both", expand=True)
        
        # 제어 패널 영역 (오른쪽)
        self.control_frame = ctk.CTkFrame(self.content_frame, width=300)
        self.control_frame.pack(side="right", fill="both", expand=False, padx=(10, 0))
        self.control_frame.pack_propagate(False)  # 이렇게 설정하면 프레임이 내부 위젯에 맞게 크기가 줄어들지 않음
        
        # 돈 투입 영역
        self.money_frame = ctk.CTkFrame(self.control_frame)
        self.money_frame.pack(fill="x", pady=(0, 10), padx=10, ipadx=5, ipady=5)
        
        # 돈 투입 레이블
        self.money_title = ctk.CTkLabel(
            self.money_frame, 
            text="돈 투입", 
            font=self.fonts["subtitle"]
        )
        self.money_title.pack(pady=(5, 10))
        
        # 현재 투입 금액 표시
        self.money_label = ctk.CTkLabel(
            self.money_frame, 
            text="투입 금액: 0원", 
            font=self.fonts["regular"]
        )
        self.money_label.pack(pady=(0, 10))
        
        # 동전 버튼 프레임
        self.coin_buttons_frame = ctk.CTkFrame(self.money_frame, fg_color="transparent")
        self.coin_buttons_frame.pack(fill="x", pady=5)
        
        # 거래 정보 영역
        self.info_frame = ctk.CTkFrame(self.control_frame)
        self.info_frame.pack(fill="x", pady=10, padx=10, ipadx=5, ipady=5)
        
        # 거래 정보 레이블
        self.info_title = ctk.CTkLabel(
            self.info_frame, 
            text="거래 정보", 
            font=self.fonts["subtitle"]
        )
        self.info_title.pack(pady=(5, 10))
        
        # 선택한 음료 정보
        self.selected_label = ctk.CTkLabel(
            self.info_frame, 
            text="선택한 음료: 없음", 
            font=self.fonts["regular"]
        )
        self.selected_label.pack(anchor="w", padx=10)
        
        self.price_label = ctk.CTkLabel(
            self.info_frame, 
            text="가격: 0원", 
            font=self.fonts["regular"]
        )
        self.price_label.pack(anchor="w", padx=10, pady=(0, 10))
        
        # 동전 버튼 생성
        self.create_coin_buttons()
        
        # 구매/취소 버튼
        self.purchase_button = ctk.CTkButton(
            self.info_frame, 
            text="구매하기", 
            command=self.purchase_drink,
            font=self.fonts["button"],
            fg_color="#2ecc71",  # 초록색
            hover_color="#27ae60"
        )
        self.purchase_button.pack(fill="x", pady=5, padx=10)
        # 이벤트 바인딩으로 반응성 향상
        self.purchase_button.bind("<ButtonPress-1>", lambda e: self.root.after(1, self.purchase_drink))
        
        self.cancel_button = ctk.CTkButton(
            self.info_frame, 
            text="취소하기", 
            command=self.cancel_transaction,
            font=self.fonts["button"],
            fg_color="#e74c3c",  # 빨간색
            hover_color="#c0392b"
        )
        self.cancel_button.pack(fill="x", pady=5, padx=10)
        # 이벤트 바인딩으로 반응성 향상
        self.cancel_button.bind("<ButtonPress-1>", lambda e: self.root.after(1, self.cancel_transaction))
        
        # 관리자 모드 버튼
        self.admin_button = ctk.CTkButton(
            self.control_frame, 
            text="관리자 모드", 
            command=self.admin_login,
            font=self.fonts["button"],
            fg_color="#3498db",  # 파란색
            hover_color="#2980b9"
        )
        self.admin_button.pack(fill="x", pady=10, padx=10)
        # 이벤트 바인딩으로 반응성 향상
        self.admin_button.bind("<ButtonPress-1>", lambda e: self.root.after(1, self.admin_login))
        
        # 선택한 음료 정보
        self.selected_drink = None
        
        # 투입 금액 초기화
        self.vending_machine.inserted_money = 0
        
        # 음료 버튼 생성
        self.create_drink_buttons()
        
        # 상태 표시줄
        self.status_frame = ctk.CTkFrame(self.root, height=30)
        self.status_frame.pack(side="bottom", fill="x")
        
        self.status_var = ctk.StringVar()
        self.status_var.set("자판기가 준비되었습니다.")
        self.status_bar = ctk.CTkLabel(
            self.status_frame, 
            textvariable=self.status_var, 
            anchor="w",
            font=self.fonts["small"]
        )
        self.status_bar.pack(side="left", fill="x", padx=10)
        
        # UI 업데이트를 강제하여 더 빠른 초기 렌더링
        self.root.update_idletasks()
    
    def create_coin_buttons(self):
        """동전 투입 버튼 생성"""
        coin_values = [50, 100, 500, 1000]
        coin_colors = ["#d1d1d1", "#a4a4a4", "#ffd700", "#ffa500"]  # 각 동전의 색상
        coin_labels = ["50원", "100원", "500원", "1000원"]  # 동전 레이블
        
        coin_frame = ctk.CTkFrame(self.coin_buttons_frame, fg_color="transparent")
        coin_frame.pack(fill="x", pady=5)
        
        # 모든 버튼에 동일한 텍스트 폰트 객체 재사용
        coin_font = ctk.CTkFont(size=16, weight="bold")
        
        for i, value in enumerate(coin_values):
            button = ctk.CTkButton(
                coin_frame, 
                text=coin_labels[i], 
                command=lambda v=value: self.insert_money(v),
                width=60,
                height=60,
                corner_radius=30,  # 동그란 버튼
                fg_color=coin_colors[i],
                hover_color="#7f8c8d",
                text_color="black",  # 검정색으로 확실하게 변경
                font=coin_font  # 캐시된 폰트 객체 사용
            )
            # 이벤트 바인딩 추가로 버튼의 반응성 향상
            button.bind("<ButtonPress-1>", lambda event, v=value: self.root.after(1, lambda: self.insert_money(v)))
            
            button.grid(row=0, column=i, padx=5, pady=5)
            coin_frame.columnconfigure(i, weight=1)
    
    def create_drink_buttons(self):
        """음료 선택 버튼 생성 - 성능 최적화"""
        # 기존 버튼 제거
        for widget in self.drinks_buttons_frame.winfo_children():
            widget.destroy()
        
        # 색상 맵 미리 정의 (매번 계산하지 않도록)
        color_map = {
            "아": "#3498db",  # 물 계열
            "레": "#e74c3c",  # 레드 계열
            "트": "#9b59b6",  # 퍼플 계열
            "칠": "#2ecc71",  # 초록 계열
            "델": "#f39c12",  # 오렌지 계열
            "포": "#1abc9c",  # 청록색 계열
            "게": "#f1c40f",  # 노란색 계열
            "핫": "#e67e22",  # 주황색 계열
            "밀": "#95a5a6",  # 회색 계열
            "코": "#d35400",  # 갈색 계열
        }
        
        # 기본 설정 값들을 미리 정의 (매번 생성하지 않게)
        default_font_bold = ctk.CTkFont(size=12, weight="bold")
        default_font = ctk.CTkFont(size=11)
        small_font = ctk.CTkFont(size=10)
        
        # 음료 버튼을 그리드로 배치
        row, col = 0, 0
        
        # 모든 음료에 대한 위젯을 한번에 생성하기 위한 리스트
        drink_widgets = []
        
        # 음료 데이터 모으기
        for idx, (name, info) in enumerate(self.vending_machine.drinks.items(), 1):
            # 음료 이름의 첫 글자에 따라 색상 선택, 없으면 기본 색상
            first_char = name[0] if name else "아"
            color = color_map.get(first_char, "#3498db")
            
            # 상태에 따라 버튼 상태 결정
            button_state = "normal" if info["stock"] > 0 else "disabled"
            stock_text = f"재고: {info['stock']}개" if info["stock"] > 0 else "품절"
            
            # 위젯 정보 저장
            drink_widgets.append({
                "name": name,
                "price": info["price"],
                "stock": info["stock"],
                "color": color,
                "state": button_state,
                "stock_text": stock_text,
                "row": row,
                "col": col
            })
            
            # 다음 위치 계산
            col += 1
            if col > 2:  # 3열로 제한
                col = 0
                row += 1
        
        # 한번에 위젯 생성 및 배치
        for widget in drink_widgets:
            # 프레임 생성
            drink_frame = ctk.CTkFrame(
                self.drinks_buttons_frame,
                width=120,
                height=150,
                corner_radius=10,
                fg_color=widget["color"]
            )
            drink_frame.grid(row=widget["row"], column=widget["col"], padx=10, pady=10, sticky="nsew")
            drink_frame.grid_propagate(False)  # 프레임 크기 고정
            
            # 전체 프레임을 클릭 가능하게 만들기
            drink_frame.bind("<Button-1>", lambda event, name=widget["name"]: self.select_drink(name))
            
            # 음료 이름
            name_label = ctk.CTkLabel(
                drink_frame,
                text=widget["name"],
                font=default_font_bold,
                wraplength=100,
                text_color="white"
            )
            name_label.pack(pady=(10, 5))
            # 라벨도 클릭 가능하게
            name_label.bind("<Button-1>", lambda event, name=widget["name"]: self.select_drink(name))
            
            # 가격 정보
            price_label = ctk.CTkLabel(
                drink_frame,
                text=f"{widget['price']}원",
                font=default_font,
                text_color="white"
            )
            price_label.pack(pady=(0, 5))
            # 라벨도 클릭 가능하게
            price_label.bind("<Button-1>", lambda event, name=widget["name"]: self.select_drink(name))
            
            # 재고 정보
            stock_label = ctk.CTkLabel(
                drink_frame,
                text=widget["stock_text"],
                font=small_font,
                text_color="white"
            )
            stock_label.pack(pady=(0, 5))
            # 라벨도 클릭 가능하게
            stock_label.bind("<Button-1>", lambda event, name=widget["name"]: self.select_drink(name))
            
            # 선택 버튼 - 클릭 영역 넓히기
            select_button = ctk.CTkButton(
                drink_frame,
                text="선택",
                command=lambda n=widget["name"]: self.select_drink(n),
                width=100,  # 더 넓은 버튼
                height=30,  # 더 높은 버튼
                corner_radius=5,
                fg_color="white",
                text_color=widget["color"],
                hover_color="#ecf0f1",
                state=widget["state"]
            )
            select_button.pack(pady=(5, 10))
        
        # 그리드 크기 조정
        for i in range(3):
            self.drinks_buttons_frame.columnconfigure(i, weight=1)
        for i in range((len(self.vending_machine.drinks) // 3) + 1):
            self.drinks_buttons_frame.rowconfigure(i, weight=1)
    
    def darken_color(self, hex_color):
        """색상을 어둡게 만드는 함수 - 캐싱 추가"""
        # 이미 계산된 색상이면 캐시에서 반환
        if hex_color in self.color_cache:
            return self.color_cache[hex_color]
            
        # 새로운 색상 계산
        r = int(hex_color[1:3], 16)
        g = int(hex_color[3:5], 16)
        b = int(hex_color[5:7], 16)
        
        # 20% 어둡게
        factor = 0.8
        r = int(r * factor)
        g = int(g * factor)
        b = int(b * factor)
        
        result = f"#{r:02x}{g:02x}{b:02x}"
        # 캐시에 저장
        self.color_cache[hex_color] = result
        return result
    
    def insert_money(self, amount):
        """돈 투입 처리"""
        # 이벤트 처리를 위해 after 메소드 사용
        def delayed_action():
            self.vending_machine.inserted_money += amount
            self.money_label.configure(text=f"투입 금액: {self.vending_machine.inserted_money}원")
            self.status_var.set(f"{amount}원이 투입되었습니다. 총 {self.vending_machine.inserted_money}원")
        
        # 약간의 지연으로 UI 업데이트를 스레드 블로킹에서 분리
        self.root.after(10, delayed_action)
    
    def select_drink(self, drink_name):
        """음료 선택 처리"""
        # 이벤트 처리를 위해 after 메소드 사용
        def delayed_action():
            self.selected_drink = drink_name
            price = self.vending_machine.drinks[drink_name]["price"]
            
            self.selected_label.configure(text=f"선택한 음료: {drink_name}")
            self.price_label.configure(text=f"가격: {price}원")
            
            self.status_var.set(f"{drink_name}을(를) 선택하셨습니다. 가격은 {price}원입니다.")
        
        # 약간의 지연으로 UI 업데이트를 스레드 블로킹에서 분리
        self.root.after(10, delayed_action)
    
    def purchase_drink(self):
        """음료 구매 처리"""
        # 음료가 선택되지 않은 경우 바로 처리
        if self.selected_drink is None:
            messagebox.showwarning("알림", "음료를 먼저 선택해주세요.")
            return
            
        # UI 블로킹 방지를 위해 짧은 지연으로 나머지 구매 로직 처리
        def process_purchase():
            try:
                # 선택된 음료가 있는지 한번 더 확인
                if self.selected_drink is None:
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
            except KeyError:
                # 예외 상황이 발생한 경우 - 음료 선택이 잘못된 경우
                messagebox.showerror("오류", "선택한 음료 정보를 찾을 수 없습니다. 다시 선택해주세요.")
                self.selected_drink = None
                self.selected_label.configure(text="선택한 음료: 없음")
                self.price_label.configure(text="가격: 0원")
                
        # 구매 로직 실행
        self.root.after(10, process_purchase)
    
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
        # 이벤트 처리를 위해 after 메소드 사용
        def delayed_action():
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
        
        # 약간의 지연으로 UI 업데이트를 스레드 블로킹에서 분리
        self.root.after(10, delayed_action)
    
    def admin_login(self):
        """관리자 로그인 처리"""
        # 짧은 지연 후 대화상자 표시 (버튼 클릭 후 곧바로 대화상자가 뜨도록)
        def delayed_action():
            password = simpledialog.askstring("관리자 로그인", "비밀번호를 입력하세요:", show='*')
            
            if password == self.vending_machine.admin_password:
                self.open_admin_window()
            elif password is not None:  # 취소가 아닌 경우만 오류 메시지
                messagebox.showerror("오류", "비밀번호가 일치하지 않습니다.")
        
        # UI 블로킹 방지
        self.root.after(10, delayed_action)
    
    def open_admin_window(self):
        """관리자 창 열기"""
        admin_window = ctk.CTkToplevel(self.root)
        admin_window.title("관리자 모드")
        admin_window.geometry("700x600")
        admin_window.grab_set()  # 모달 창으로 설정
        
        # 탭 생성
        tabview = ctk.CTkTabview(admin_window)
        tabview.pack(fill="both", expand=True, padx=20, pady=20)
        
        # 탭 추가
        tab_stock = tabview.add("재고 관리")
        tab_coin = tabview.add("동전 관리")
        tab_system = tabview.add("시스템")
        
        # 재고 관리 탭 내용
        stock_frame = ctk.CTkFrame(tab_stock)
        stock_frame.pack(fill="both", expand=True, padx=10, pady=10)
        
        # 음료 재고 레이블
        stock_label = ctk.CTkLabel(
            stock_frame, 
            text="음료 재고 관리", 
            font=ctk.CTkFont(size=16, weight="bold")
        )
        stock_label.pack(pady=(10, 20))
        
        # 음료 스크롤 프레임
        stock_scroll = ctk.CTkScrollableFrame(stock_frame)
        stock_scroll.pack(fill="both", expand=True, padx=10, pady=10)
        
        # 음료 목록을 그리드로 표시
        headers = ["음료명", "가격 (원)", "재고", ""]
        for i, header in enumerate(headers):
            label = ctk.CTkLabel(
                stock_scroll, 
                text=header, 
                font=ctk.CTkFont(size=14, weight="bold")
            )
            label.grid(row=0, column=i, padx=10, pady=5, sticky="w")
        
        # 음료 데이터 행 추가
        for idx, (name, info) in enumerate(self.vending_machine.drinks.items(), 1):
            # 음료명
            name_label = ctk.CTkLabel(
                stock_scroll, 
                text=name, 
                font=ctk.CTkFont(size=12)
            )
            name_label.grid(row=idx, column=0, padx=10, pady=5, sticky="w")
            
            # 가격
            price_label = ctk.CTkLabel(
                stock_scroll, 
                text=str(info["price"]), 
                font=ctk.CTkFont(size=12)
            )
            price_label.grid(row=idx, column=1, padx=10, pady=5, sticky="w")
            
            # 재고
            stock_label = ctk.CTkLabel(
                stock_scroll, 
                text=str(info["stock"]), 
                font=ctk.CTkFont(size=12)
            )
            stock_label.grid(row=idx, column=2, padx=10, pady=5, sticky="w")
            
            # 작업 버튼 프레임
            action_frame = ctk.CTkFrame(stock_scroll, fg_color="transparent")
            action_frame.grid(row=idx, column=3, padx=10, pady=5, sticky="w")
            
            # 재고 추가 버튼
            add_btn = ctk.CTkButton(
                action_frame,
                text="재고 추가",
                command=lambda n=name: self.add_stock_dialog(n),
                width=80,
                height=25,
                fg_color="#2ecc71",
                hover_color="#27ae60",
                font=ctk.CTkFont(size=12)
            )
            add_btn.pack(side="left", padx=(0, 5))
            
            # 가격 수정 버튼
            price_btn = ctk.CTkButton(
                action_frame,
                text="가격 수정",
                command=lambda n=name: self.change_price_dialog(n),
                width=80,
                height=25,
                fg_color="#3498db",
                hover_color="#2980b9",
                font=ctk.CTkFont(size=12)
            )
            price_btn.pack(side="left")
        
        # 동전 관리 탭 내용
        coin_frame = ctk.CTkFrame(tab_coin)
        coin_frame.pack(fill="both", expand=True, padx=10, pady=10)
        
        # 동전 재고 레이블
        coin_label = ctk.CTkLabel(
            coin_frame, 
            text="동전 재고 관리", 
            font=ctk.CTkFont(size=16, weight="bold")
        )
        coin_label.pack(pady=(10, 20))
        
        # 동전 정보를 표시할 프레임
        coin_info_frame = ctk.CTkFrame(coin_frame)
        coin_info_frame.pack(fill="x", expand=False, padx=10, pady=10)
        
        # 동전 종류와 개수 표시
        coin_names = {1000: "1000원 지폐", 500: "500원 동전", 100: "100원 동전", 50: "50원 동전"}
        for i, (coin, name) in enumerate(coin_names.items()):
            # 동전 행 프레임
            coin_row = ctk.CTkFrame(coin_info_frame)
            coin_row.pack(fill="x", pady=5)
            
            # 동전 이름
            name_label = ctk.CTkLabel(
                coin_row,
                text=name,
                font=ctk.CTkFont(size=14),
                width=120
            )
            name_label.pack(side="left", padx=10)
            
            # 동전 개수
            count_label = ctk.CTkLabel(
                coin_row,
                text=f"{self.vending_machine.coins[coin]}개",
                font=ctk.CTkFont(size=14),
                width=80
            )
            count_label.pack(side="left", padx=10)
            
            # 동전 추가 버튼
            add_coin_btn = ctk.CTkButton(
                coin_row,
                text="추가",
                command=lambda c=coin, n=name: self.add_coin_dialog(c, n),
                width=60,
                height=30,
                fg_color="#2ecc71",
                hover_color="#27ae60",
                font=ctk.CTkFont(size=12)
            )
            add_coin_btn.pack(side="right", padx=10)
        
        # 시스템 탭 내용
        system_frame = ctk.CTkFrame(tab_system)
        system_frame.pack(fill="both", expand=True, padx=10, pady=10)
        
        # 시스템 레이블
        system_label = ctk.CTkLabel(
            system_frame, 
            text="시스템 설정", 
            font=ctk.CTkFont(size=16, weight="bold")
        )
        system_label.pack(pady=(10, 20))
        
        # 비밀번호 변경 프레임
        pwd_frame = ctk.CTkFrame(system_frame)
        pwd_frame.pack(fill="x", padx=10, pady=10)
        
        # 비밀번호 변경 레이블
        pwd_label = ctk.CTkLabel(
            pwd_frame, 
            text="비밀번호 변경", 
            font=ctk.CTkFont(size=14, weight="bold")
        )
        pwd_label.pack(pady=(5, 10))
        
        # 비밀번호 입력 필드
        pwd_entry = ctk.CTkEntry(pwd_frame, show='*', width=200)
        pwd_entry.pack(pady=5)
        
        # 비밀번호 변경 버튼
        change_pwd_btn = ctk.CTkButton(
            pwd_frame,
            text="비밀번호 변경",
            command=lambda: self.change_password(pwd_entry.get()),
            font=ctk.CTkFont(size=12),
            fg_color="#3498db",
            hover_color="#2980b9"
        )
        change_pwd_btn.pack(pady=10)
        
        # 자판기 초기화 프레임
        reset_frame = ctk.CTkFrame(system_frame)
        reset_frame.pack(fill="x", padx=10, pady=(20, 10))
        
        # 초기화 레이블
        reset_label = ctk.CTkLabel(
            reset_frame, 
            text="자판기 초기화", 
            font=ctk.CTkFont(size=14, weight="bold")
        )
        reset_label.pack(pady=(5, 10))
        
        # 초기화 버튼
        reset_btn = ctk.CTkButton(
            reset_frame,
            text="자판기 초기화",
            command=self.confirm_reset,
            font=ctk.CTkFont(size=12),
            fg_color="#e74c3c",
            hover_color="#c0392b"
        )
        reset_btn.pack(pady=10)
        
        # 창 닫기 버튼
        close_btn = ctk.CTkButton(
            admin_window,
            text="관리자 모드 종료",
            command=admin_window.destroy,
            font=ctk.CTkFont(size=14),
            width=120,
            height=40
        )
        close_btn.pack(pady=20)
    
    def add_stock_dialog(self, drink_name):
        """재고 추가 대화상자"""
        result = simpledialog.askinteger(
            "재고 추가", 
            f"{drink_name}의 추가할 수량을 입력하세요:"
        )
        
        if result is not None and result > 0:
            self.vending_machine.drinks[drink_name]["stock"] += result
            messagebox.showinfo("알림", f"{drink_name}의 재고가 {result}개 추가되었습니다.")
            self.create_drink_buttons()  # 음료 버튼 갱신
        elif result is not None:
            messagebox.showwarning("알림", "유효한 수량을 입력하세요.")
    
    def change_price_dialog(self, drink_name):
        """가격 변경 대화상자"""
        current_price = self.vending_machine.drinks[drink_name]["price"]
        result = simpledialog.askinteger(
            "가격 변경",
            f"{drink_name}의 새 가격을 입력하세요 (현재: {current_price}원):"
        )
        
        if result is not None and result > 0:
            self.vending_machine.drinks[drink_name]["price"] = result
            messagebox.showinfo(
                "알림",
                f"{drink_name}의 가격이 {current_price}원에서 {result}원으로 변경되었습니다."
            )
            self.create_drink_buttons()  # 음료 버튼 갱신
        elif result is not None:
            messagebox.showwarning("알림", "유효한 가격을 입력하세요.")
    
    def add_coin_dialog(self, coin_value, coin_name):
        """동전 추가 대화상자"""
        result = simpledialog.askinteger(
            "동전 추가",
            f"{coin_name}의 추가할 수량을 입력하세요:"
        )
        
        if result is not None and result > 0:
            self.vending_machine.coins[coin_value] += result
            messagebox.showinfo(
                "알림",
                f"{coin_name}이(가) {result}개 추가되었습니다. 현재 {self.vending_machine.coins[coin_value]}개가 있습니다."
            )
        elif result is not None:
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
    root = ctk.CTk()
    app = VendingMachineGUI(root)
    root.mainloop() 