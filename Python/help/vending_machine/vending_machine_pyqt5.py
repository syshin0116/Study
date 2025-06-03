#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
from PyQt5.QtWidgets import (QApplication, QMainWindow, QPushButton, QFrame, QLabel, 
                           QVBoxLayout, QHBoxLayout, QGridLayout, QScrollArea, 
                           QWidget, QMessageBox, QInputDialog, QSizePolicy, QTabWidget, QGroupBox, QLineEdit,
                           QDialog)
from PyQt5.QtCore import Qt, QSize
from PyQt5.QtGui import QFont, QColor, QPalette
from vending_machine import VendingMachine

class ModeSelectorDialog(QDialog):
    """시작 모드 선택 다이얼로그"""
    def __init__(self, vending_machine, parent=None):
        super().__init__(parent)
        self.vending_machine = vending_machine
        self.selected_mode = None
        self.initUI()
    
    def initUI(self):
        """UI 초기화"""
        self.setWindowTitle("자판기 모드 선택")
        self.setGeometry(100, 100, 1000, 700)
        
        main_layout = QVBoxLayout(self)
        main_layout.setContentsMargins(100, 100, 100, 100)
        
        # 제목
        title_label = QLabel("음료 자판기 모드 선택")
        title_label.setAlignment(Qt.AlignCenter)
        title_label.setFont(QFont('Arial', 28, QFont.Bold))
        main_layout.addWidget(title_label)
        
        # 여백 추가
        main_layout.addSpacing(60)
        
        # 버튼 컨테이너 (가운데 정렬을 위한 HBox)
        button_container = QHBoxLayout()
        button_container.setSpacing(40)  # 버튼 사이 간격
        
        # 왼쪽 여백을 위한 스트레치
        button_container.addStretch(1)
        
        # 소비자 모드 버튼
        consumer_btn = QPushButton("소비자 모드")
        consumer_btn.setStyleSheet("""
            background-color: #3498db; 
            color: white;
            border-radius: 15px;
            padding: 10px;
        """)
        consumer_btn.setFont(QFont('Arial', 20, QFont.Bold))
        consumer_btn.setFixedSize(300, 180)
        consumer_btn.clicked.connect(self.select_consumer_mode)
        button_container.addWidget(consumer_btn)
        
        # 관리자 모드 버튼
        admin_btn = QPushButton("관리자 모드")
        admin_btn.setStyleSheet("""
            background-color: #e74c3c; 
            color: white;
            border-radius: 15px;
            padding: 10px;
        """)
        admin_btn.setFont(QFont('Arial', 20, QFont.Bold))
        admin_btn.setFixedSize(300, 180)
        admin_btn.clicked.connect(self.select_admin_mode)
        button_container.addWidget(admin_btn)
        
        # 오른쪽 여백을 위한 스트레치
        button_container.addStretch(1)
        
        main_layout.addLayout(button_container)
        
        # 버튼과 종료 버튼 사이 여백
        main_layout.addStretch(1)
        
        # 종료 버튼 컨테이너 (가운데 정렬)
        exit_container = QHBoxLayout()
        exit_container.addStretch(1)
        
        # 종료 버튼
        exit_btn = QPushButton("종료")
        exit_btn.setStyleSheet("""
            background-color: #7f8c8d; 
            color: white;
            border-radius: 10px;
            padding: 8px;
        """)
        exit_btn.setFont(QFont('Arial', 16, QFont.Bold))
        exit_btn.setFixedSize(200, 60)
        exit_btn.clicked.connect(self.reject)
        exit_container.addWidget(exit_btn)
        
        exit_container.addStretch(1)
        main_layout.addLayout(exit_container)
        
        # 아래 여백
        main_layout.addSpacing(30)
    
    def select_consumer_mode(self):
        """소비자 모드 선택"""
        self.selected_mode = "consumer"
        self.accept()
    
    def select_admin_mode(self):
        """관리자 모드 선택"""
        # 비밀번호 확인
        password, ok = QInputDialog.getText(
            self, "관리자 로그인", "비밀번호를 입력하세요:", 
            QLineEdit.Password
        )
        
        if ok and password == self.vending_machine.admin_password:
            self.selected_mode = "admin"
            self.accept()
        elif ok:
            QMessageBox.critical(self, "오류", "비밀번호가 일치하지 않습니다.")

class DrinkButton(QFrame):
    """음료 선택 버튼 위젯"""
    def __init__(self, name, price, stock, color, parent=None):
        super().__init__(parent)
        self.name = name
        self.price = price
        self.stock = stock
        
        # 스타일 설정
        self.setFrameShape(QFrame.StyledPanel)
        self.setFrameShadow(QFrame.Raised)
        self.setStyleSheet(f"background-color: {color}; border-radius: 10px; color: white;")
        
        # 레이아웃 설정
        layout = QVBoxLayout()
        self.setLayout(layout)
        
        # 음료 이름 라벨
        self.name_label = QLabel(name)
        self.name_label.setAlignment(Qt.AlignCenter)
        self.name_label.setFont(QFont('Arial', 12, QFont.Bold))
        self.name_label.setStyleSheet("color: white;")
        layout.addWidget(self.name_label)
        
        # 가격 라벨
        self.price_label = QLabel(f"{price}원")
        self.price_label.setAlignment(Qt.AlignCenter)
        self.price_label.setFont(QFont('Arial', 10))
        self.price_label.setStyleSheet("color: white;")
        layout.addWidget(self.price_label)
        
        # 재고 라벨
        stock_text = f"재고: {stock}개" if stock > 0 else "품절"
        self.stock_label = QLabel(stock_text)
        self.stock_label.setAlignment(Qt.AlignCenter)
        self.stock_label.setFont(QFont('Arial', 9))
        self.stock_label.setStyleSheet("color: white;")
        layout.addWidget(self.stock_label)
        
        # 선택 버튼
        self.select_button = QPushButton("선택")
        self.select_button.setEnabled(stock > 0)
        self.select_button.setStyleSheet("""
            background-color: white;
            color: black;
            border-radius: 5px;
            padding: 5px;
        """)
        layout.addWidget(self.select_button)
        
        # 고정 크기 설정
        self.setMinimumSize(120, 150)
        self.setMaximumSize(120, 150)

class CoinButton(QPushButton):
    """동전 버튼 위젯"""
    def __init__(self, value, color, parent=None):
        super().__init__(f"{value}원", parent)
        self.value = value
        
        # 동전 스타일 설정
        self.setStyleSheet(f"""
            background-color: {color};
            color: black;
            border-radius: 25px;
            font-weight: bold;
        """)
        self.setMinimumSize(50, 50)
        self.setMaximumSize(50, 50)
        self.setFont(QFont('Arial', 10, QFont.Bold))

class VendingMachineGUI(QMainWindow):
    def __init__(self, vending_machine):
        super().__init__()
        self.vending_machine = vending_machine  # 자판기 객체
        self.selected_drink = None  # 선택한 음료
        self.initUI()
        
    def initUI(self):
        """UI 초기화"""
        self.setWindowTitle("음료 자판기")
        self.setGeometry(100, 100, 1000, 700)
        
        # 메인 위젯 및 레이아웃
        main_widget = QWidget()
        self.setCentralWidget(main_widget)
        main_layout = QVBoxLayout(main_widget)
        
        # 제목
        title_label = QLabel("음료 자판기")
        title_label.setAlignment(Qt.AlignCenter)
        title_label.setFont(QFont('Arial', 24, QFont.Bold))
        main_layout.addWidget(title_label)
        
        # 메인 콘텐츠 영역 (음료 선택 + 제어 패널)
        content_layout = QHBoxLayout()
        main_layout.addLayout(content_layout)
        
        # ----- 음료 선택 영역 (왼쪽) -----
        self.drinks_widget = QWidget()
        drinks_layout = QVBoxLayout(self.drinks_widget)
        
        # 음료 메뉴 라벨
        drinks_label = QLabel("음료 메뉴")
        drinks_label.setFont(QFont('Arial', 18, QFont.Bold))
        drinks_label.setAlignment(Qt.AlignCenter)
        drinks_layout.addWidget(drinks_label)
        
        # 음료 스크롤 영역
        scroll_area = QScrollArea()
        scroll_area.setWidgetResizable(True)
        drinks_layout.addWidget(scroll_area)
        
        # 스크롤 내용물 (음료 버튼들)
        self.scroll_content = QWidget()
        self.drinks_grid = QGridLayout(self.scroll_content)
        scroll_area.setWidget(self.scroll_content)
        
        # 음료 버튼 생성
        self.create_drink_buttons()
        
        content_layout.addWidget(self.drinks_widget, 3)  # 비율 3
        
        # ----- 제어 패널 (오른쪽) -----
        control_panel = QWidget()
        control_layout = QVBoxLayout(control_panel)
        control_panel.setMinimumWidth(300)
        control_panel.setMaximumWidth(300)
        
        # 돈 투입 영역
        money_frame = QFrame()
        money_frame.setFrameShape(QFrame.StyledPanel)
        money_layout = QVBoxLayout(money_frame)
        
        # 돈 투입 제목
        money_title = QLabel("돈 투입")
        money_title.setFont(QFont('Arial', 16, QFont.Bold))
        money_title.setAlignment(Qt.AlignCenter)
        money_layout.addWidget(money_title)
        
        # 투입 금액 표시
        self.money_label = QLabel("투입 금액: 0원")
        self.money_label.setFont(QFont('Arial', 14))
        self.money_label.setAlignment(Qt.AlignCenter)
        money_layout.addWidget(self.money_label)
        
        # 동전 버튼들
        coin_layout = QHBoxLayout()
        money_layout.addLayout(coin_layout)
        
        # 동전 버튼 생성
        coin_values = [50, 100, 500, 1000]
        coin_colors = ["lightgray", "darkgray", "gold", "orange"]
        
        for value, color in zip(coin_values, coin_colors):
            coin_button = CoinButton(value, color)
            coin_button.clicked.connect(lambda checked, v=value: self.insert_money(v))
            coin_layout.addWidget(coin_button)
        
        control_layout.addWidget(money_frame)
        
        # 거래 정보 영역
        info_frame = QFrame()
        info_frame.setFrameShape(QFrame.StyledPanel)
        info_layout = QVBoxLayout(info_frame)
        
        # 거래 정보 제목
        info_title = QLabel("거래 정보")
        info_title.setFont(QFont('Arial', 16, QFont.Bold))
        info_title.setAlignment(Qt.AlignCenter)
        info_layout.addWidget(info_title)
        
        # 선택한 음료 정보
        self.selected_label = QLabel("선택한 음료: 없음")
        self.selected_label.setFont(QFont('Arial', 14))
        info_layout.addWidget(self.selected_label)
        
        # 가격 정보
        self.price_label = QLabel("가격: 0원")
        self.price_label.setFont(QFont('Arial', 14))
        info_layout.addWidget(self.price_label)
        
        # 구매 버튼
        purchase_button = QPushButton("구매하기")
        purchase_button.setStyleSheet("background-color: #2ecc71; color: white;")
        purchase_button.setFont(QFont('Arial', 14))
        purchase_button.clicked.connect(self.purchase_drink)
        info_layout.addWidget(purchase_button)
        
        # 취소 버튼
        cancel_button = QPushButton("취소하기")
        cancel_button.setStyleSheet("background-color: #e74c3c; color: white;")
        cancel_button.setFont(QFont('Arial', 14))
        cancel_button.clicked.connect(self.cancel_transaction)
        info_layout.addWidget(cancel_button)
        
        control_layout.addWidget(info_frame)
        
        # 관리자 모드 버튼
        admin_button = QPushButton("관리자 모드")
        admin_button.setStyleSheet("background-color: #3498db; color: white;")
        admin_button.setFont(QFont('Arial', 14))
        admin_button.clicked.connect(self.admin_login)
        control_layout.addWidget(admin_button)
        
        # 스페이서 추가 (빈 공간)
        control_layout.addStretch(1)
        
        content_layout.addWidget(control_panel)
        
        # 상태 표시줄
        self.statusBar().showMessage("자판기가 준비되었습니다.")
    
    def create_drink_buttons(self):
        """음료 선택 버튼 생성"""
        # 기존 버튼 제거
        for i in reversed(range(self.drinks_grid.count())): 
            self.drinks_grid.itemAt(i).widget().setParent(None)
        
        # 색상 매핑
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
        
        # 음료 버튼 그리드에 배치
        row, col = 0, 0
        for name, info in self.vending_machine.drinks.items():
            # 음료 이름의 첫 글자에 따라 색상 선택
            first_char = name[0] if name else "아"
            color = color_map.get(first_char, "#3498db")  # 기본 색상
            
            # 음료 버튼 생성
            drink_button = DrinkButton(name, info["price"], info["stock"], color)
            
            # 선택 버튼에 이벤트 연결
            drink_button.select_button.clicked.connect(lambda checked, n=name: self.select_drink(n))
            
            # 그리드에 추가
            self.drinks_grid.addWidget(drink_button, row, col)
            
            # 다음 위치 계산
            col += 1
            if col > 2:  # 3열로 제한
                col = 0
                row += 1
    
    def select_drink(self, drink_name):
        """음료 선택 처리"""
        self.selected_drink = drink_name
        price = self.vending_machine.drinks[drink_name]["price"]
        
        self.selected_label.setText(f"선택한 음료: {drink_name}")
        self.price_label.setText(f"가격: {price}원")
        
        self.statusBar().showMessage(f"{drink_name}을(를) 선택하셨습니다. 가격은 {price}원입니다.")
    
    def insert_money(self, amount):
        """돈 투입 처리"""
        self.vending_machine.inserted_money += amount
        self.money_label.setText(f"투입 금액: {self.vending_machine.inserted_money}원")
        self.statusBar().showMessage(f"{amount}원이 투입되었습니다. 총 {self.vending_machine.inserted_money}원")
    
    def purchase_drink(self):
        """음료 구매 처리"""
        # 음료가 선택되지 않은 경우
        if self.selected_drink is None:
            QMessageBox.warning(self, "알림", "음료를 먼저 선택해주세요.")
            return
            
        try:
            drink_info = self.vending_machine.drinks[self.selected_drink]
            price = drink_info["price"]
            
            # 금액 부족 확인
            if self.vending_machine.inserted_money < price:
                shortage = price - self.vending_machine.inserted_money
                QMessageBox.warning(self, "금액 부족", f"금액이 {shortage}원 부족합니다.")
                return
            
            # 재고 확인
            if drink_info["stock"] <= 0:
                QMessageBox.warning(self, "품절", f"{self.selected_drink}이(가) 품절되었습니다.")
                self.create_drink_buttons()  # 버튼 갱신
                return
            
            # 거스름돈 계산
            change = self.vending_machine.inserted_money - price
            
            # 거스름돈 반환 가능 여부 확인
            if not self.vending_machine.check_change(change):
                QMessageBox.critical(self, "오류", "거스름돈이 부족합니다. 관리자에게 문의하세요.")
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
            QMessageBox.information(self, "구매 완료", message)
            
            # 상태 초기화
            self.selected_drink = None
            self.vending_machine.inserted_money = 0
            self.money_label.setText("투입 금액: 0원")
            self.selected_label.setText("선택한 음료: 없음")
            self.price_label.setText("가격: 0원")
            self.statusBar().showMessage("구매가 완료되었습니다. 이용해 주셔서 감사합니다.")
            
            # 음료 버튼 갱신
            self.create_drink_buttons()
        
        except KeyError:
            QMessageBox.critical(self, "오류", "선택한 음료 정보를 찾을 수 없습니다. 다시 선택해주세요.")
            self.selected_drink = None
            self.selected_label.setText("선택한 음료: 없음")
            self.price_label.setText("가격: 0원")
    
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
            QMessageBox.information(self, "거래 취소", f"거래가 취소되었습니다.\n\n{change_message}")
            
            # 상태 초기화
            self.vending_machine.inserted_money = 0
            self.money_label.setText("투입 금액: 0원")
            self.selected_drink = None
            self.selected_label.setText("선택한 음료: 없음")
            self.price_label.setText("가격: 0원")
            self.statusBar().showMessage("거래가 취소되었습니다.")
        else:
            QMessageBox.information(self, "알림", "취소할 거래가 없습니다.")
    
    def admin_login(self):
        """관리자 로그인 처리"""
        password, ok = QInputDialog.getText(
            self, "관리자 로그인", "비밀번호를 입력하세요:", 
            QLineEdit.Password
        )
        
        if ok and password == self.vending_machine.admin_password:
            self.open_admin_window()
        elif ok:
            QMessageBox.critical(self, "오류", "비밀번호가 일치하지 않습니다.")
    
    def open_admin_window(self):
        """관리자 창 열기"""
        admin_dialog = AdminDialog(self.vending_machine, self)
        admin_dialog.exec_()
        
        # 관리자 창 닫힌 후 음료 버튼 갱신
        self.create_drink_buttons()

class AdminDialog(QDialog):
    """관리자 모드 다이얼로그"""
    def __init__(self, vending_machine, parent=None):
        super().__init__(parent)
        self.vending_machine = vending_machine
        self.initUI()
        
    def initUI(self):
        """UI 초기화"""
        self.setWindowTitle("관리자 모드")
        self.setGeometry(200, 200, 700, 600)
        
        # 탭 위젯 생성
        self.tabs = QTabWidget(self)
        main_layout = QVBoxLayout(self)
        main_layout.addWidget(self.tabs)
        
        # 탭 추가
        self.create_stock_tab()  # 재고 관리 탭
        self.create_coin_tab()   # 동전 관리 탭
        self.create_system_tab() # 시스템 탭
        
        # 닫기 버튼
        close_button = QPushButton("관리자 모드 종료")
        close_button.setFont(QFont('Arial', 14))
        close_button.clicked.connect(self.accept)
        main_layout.addWidget(close_button)
    
    def create_stock_tab(self):
        """재고 관리 탭 생성"""
        stock_tab = QWidget()
        self.tabs.addTab(stock_tab, "재고 관리")
        
        layout = QVBoxLayout(stock_tab)
        
        # 제목
        title = QLabel("음료 재고 관리")
        title.setFont(QFont('Arial', 16, QFont.Bold))
        title.setAlignment(Qt.AlignCenter)
        layout.addWidget(title)
        
        # 음료 목록 표시 영역
        scroll = QScrollArea()
        scroll.setWidgetResizable(True)
        layout.addWidget(scroll)
        
        scroll_content = QWidget()
        scroll_layout = QVBoxLayout(scroll_content)
        scroll.setWidget(scroll_content)
        
        # 헤더
        header_layout = QHBoxLayout()
        headers = ["음료명", "가격 (원)", "재고", ""]
        
        for header in headers:
            label = QLabel(header)
            label.setFont(QFont('Arial', 14, QFont.Bold))
            header_layout.addWidget(label)
        
        scroll_layout.addLayout(header_layout)
        
        # 음료 항목 추가
        for name, info in self.vending_machine.drinks.items():
            item_layout = QHBoxLayout()
            
            # 음료명
            name_label = QLabel(name)
            name_label.setFont(QFont('Arial', 12))
            item_layout.addWidget(name_label)
            
            # 가격
            price_label = QLabel(str(info["price"]))
            price_label.setFont(QFont('Arial', 12))
            item_layout.addWidget(price_label)
            
            # 재고
            stock_label = QLabel(str(info["stock"]))
            stock_label.setFont(QFont('Arial', 12))
            item_layout.addWidget(stock_label)
            
            # 작업 버튼
            button_layout = QHBoxLayout()
            
            # 재고 추가 버튼
            add_btn = QPushButton("재고 추가")
            add_btn.setStyleSheet("background-color: #2ecc71; color: white;")
            add_btn.clicked.connect(lambda checked, n=name: self.add_stock_dialog(n))
            button_layout.addWidget(add_btn)
            
            # 가격 수정 버튼
            price_btn = QPushButton("가격 수정")
            price_btn.setStyleSheet("background-color: #3498db; color: white;")
            price_btn.clicked.connect(lambda checked, n=name: self.change_price_dialog(n))
            button_layout.addWidget(price_btn)
            
            item_layout.addLayout(button_layout)
            scroll_layout.addLayout(item_layout)
    
    def create_coin_tab(self):
        """동전 관리 탭 생성"""
        coin_tab = QWidget()
        self.tabs.addTab(coin_tab, "동전 관리")
        
        layout = QVBoxLayout(coin_tab)
        
        # 제목
        title = QLabel("동전 재고 관리")
        title.setFont(QFont('Arial', 16, QFont.Bold))
        title.setAlignment(Qt.AlignCenter)
        layout.addWidget(title)
        
        # 동전 정보 표시
        coin_names = {1000: "1000원 지폐", 500: "500원 동전", 100: "100원 동전", 50: "50원 동전"}
        
        for coin, name in coin_names.items():
            item_layout = QHBoxLayout()
            
            # 동전 이름
            name_label = QLabel(name)
            name_label.setFont(QFont('Arial', 14))
            item_layout.addWidget(name_label)
            
            # 동전 개수
            count_label = QLabel(f"{self.vending_machine.coins[coin]}개")
            count_label.setFont(QFont('Arial', 14))
            item_layout.addWidget(count_label)
            
            # 동전 추가 버튼
            add_btn = QPushButton("추가")
            add_btn.setStyleSheet("background-color: #2ecc71; color: white;")
            add_btn.clicked.connect(lambda checked, c=coin, n=name: self.add_coin_dialog(c, n))
            item_layout.addWidget(add_btn)
            
            layout.addLayout(item_layout)
        
        # 공백 추가
        layout.addStretch(1)
    
    def create_system_tab(self):
        """시스템 탭 생성"""
        system_tab = QWidget()
        self.tabs.addTab(system_tab, "시스템")
        
        layout = QVBoxLayout(system_tab)
        
        # 제목
        title = QLabel("시스템 설정")
        title.setFont(QFont('Arial', 16, QFont.Bold))
        title.setAlignment(Qt.AlignCenter)
        layout.addWidget(title)
        
        # 비밀번호 변경 영역
        pwd_group = QGroupBox("비밀번호 변경")
        pwd_layout = QVBoxLayout(pwd_group)
        
        # 비밀번호 입력 필드
        self.pwd_input = QLineEdit()
        self.pwd_input.setEchoMode(QLineEdit.Password)
        pwd_layout.addWidget(self.pwd_input)
        
        # 비밀번호 변경 버튼
        pwd_btn = QPushButton("비밀번호 변경")
        pwd_btn.setStyleSheet("background-color: #3498db; color: white;")
        pwd_btn.clicked.connect(self.change_password)
        pwd_layout.addWidget(pwd_btn)
        
        layout.addWidget(pwd_group)
        
        # 자판기 초기화 영역
        reset_group = QGroupBox("자판기 초기화")
        reset_layout = QVBoxLayout(reset_group)
        
        # 초기화 버튼
        reset_btn = QPushButton("자판기 초기화")
        reset_btn.setStyleSheet("background-color: #e74c3c; color: white;")
        reset_btn.clicked.connect(self.confirm_reset)
        reset_layout.addWidget(reset_btn)
        
        layout.addWidget(reset_group)
        
        # 공백 추가
        layout.addStretch(1)
    
    def add_stock_dialog(self, drink_name):
        """재고 추가 대화상자"""
        quantity, ok = QInputDialog.getInt(
            self, "재고 추가", f"{drink_name}의 추가할 수량을 입력하세요:",
            0, 0, 1000, 1
        )
        
        if ok and quantity > 0:
            self.vending_machine.drinks[drink_name]["stock"] += quantity
            QMessageBox.information(
                self, "알림", f"{drink_name}의 재고가 {quantity}개 추가되었습니다."
            )
            self.update_tabs()
        elif ok and quantity <= 0:
            QMessageBox.warning(self, "알림", "유효한 수량을 입력하세요.")
    
    def change_price_dialog(self, drink_name):
        """가격 변경 대화상자"""
        current_price = self.vending_machine.drinks[drink_name]["price"]
        new_price, ok = QInputDialog.getInt(
            self, "가격 변경", 
            f"{drink_name}의 새 가격을 입력하세요 (현재: {current_price}원):",
            current_price, 1, 100000, 100
        )
        
        if ok and new_price > 0:
            self.vending_machine.drinks[drink_name]["price"] = new_price
            QMessageBox.information(
                self, "알림",
                f"{drink_name}의 가격이 {current_price}원에서 {new_price}원으로 변경되었습니다."
            )
            self.update_tabs()
        elif ok and new_price <= 0:
            QMessageBox.warning(self, "알림", "유효한 가격을 입력하세요.")
    
    def add_coin_dialog(self, coin_value, coin_name):
        """동전 추가 대화상자"""
        quantity, ok = QInputDialog.getInt(
            self, "동전 추가", f"{coin_name}의 추가할 수량을 입력하세요:",
            0, 0, 1000, 1
        )
        
        if ok and quantity > 0:
            self.vending_machine.coins[coin_value] += quantity
            QMessageBox.information(
                self, "알림",
                f"{coin_name}이(가) {quantity}개 추가되었습니다. 현재 {self.vending_machine.coins[coin_value]}개가 있습니다."
            )
            self.update_tabs()
        elif ok and quantity <= 0:
            QMessageBox.warning(self, "알림", "유효한 수량을 입력하세요.")
    
    def change_password(self):
        """비밀번호 변경 처리"""
        new_password = self.pwd_input.text()
        
        if not new_password:
            QMessageBox.warning(self, "알림", "비밀번호를 입력하세요.")
            return
        
        self.vending_machine.admin_password = new_password
        QMessageBox.information(self, "알림", "비밀번호가 변경되었습니다.")
    
    def confirm_reset(self):
        """자판기 초기화 확인"""
        reply = QMessageBox.question(
            self, "확인", "정말 자판기를 초기화하시겠습니까?",
            QMessageBox.Yes | QMessageBox.No, QMessageBox.No
        )
        
        if reply == QMessageBox.Yes:
            # 자판기 초기화
            self.vending_machine.__init__()
            QMessageBox.information(self, "알림", "자판기가 초기화되었습니다.")
            self.update_tabs()
    
    def update_tabs(self):
        """탭 내용 업데이트 (재고/동전 변경 후)"""
        # 현재 탭 인덱스 저장
        current_index = self.tabs.currentIndex()
        
        # 탭 제거
        while self.tabs.count() > 0:
            self.tabs.removeTab(0)
        
        # 탭 다시 생성
        self.create_stock_tab()
        self.create_coin_tab()
        self.create_system_tab()
        
        # 이전 탭으로 복원
        self.tabs.setCurrentIndex(current_index)

if __name__ == "__main__":
    app = QApplication(sys.argv)
    vending_machine = VendingMachine()
    
    # 모드 선택 다이얼로그 표시
    mode_selector = ModeSelectorDialog(vending_machine)
    if mode_selector.exec_() == QDialog.Accepted:
        selected_mode = mode_selector.selected_mode
        
        if selected_mode == "consumer":
            # 소비자 모드로 시작
            window = VendingMachineGUI(vending_machine)
            window.show()
            sys.exit(app.exec_())
        elif selected_mode == "admin":
            # 관리자 모드로 시작
            admin_dialog = AdminDialog(vending_machine)
            admin_dialog.exec_()
            # 관리자 모드 종료 후 소비자 모드로 전환
            window = VendingMachineGUI(vending_machine)
            window.show()
            sys.exit(app.exec_())
    else:
        # 모드 선택 취소 시 프로그램 종료
        sys.exit(0) 