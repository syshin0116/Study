class VendingMachine:
    def __init__(self):
        # 음료 정보 초기화 (이름, 가격, 재고)
        self.drinks = {
            "아이시스 8.0": {"price": 800, "stock": 10},
            "아쿠아 제로": {"price": 2000, "stock": 10},
            "레몬워터 C1000": {"price": 1800, "stock": 10},
            "옥수수 수염차": {"price": 1600, "stock": 10},
            "트레비": {"price": 1300, "stock": 10},
            "펩시 제로슈거": {"price": 1100, "stock": 10},
            "펩시": {"price": 1100, "stock": 10},
            "칠성 사이다 제로": {"price": 1300, "stock": 10},
            "칠성사이다": {"price": 1300, "stock": 10},
            "델몬트 망고": {"price": 1200, "stock": 10},
            "립톤 제로": {"price": 1200, "stock": 10},
            "사과 에이드": {"price": 1100, "stock": 10},
            "포도 에이드": {"price": 1100, "stock": 10},
            "가나초코": {"price": 900, "stock": 10},
            "레쓰비": {"price": 900, "stock": 10},
            "핫식스 제로": {"price": 1300, "stock": 10},
            "밀키스": {"price": 1100, "stock": 10},
            "핫식스": {"price": 1300, "stock": 10},
            "레쓰비 카페타임 라떼": {"price": 1200, "stock": 10},
            "게토레이": {"price": 1200, "stock": 10},
            "코코넛 포도": {"price": 1000, "stock": 10},
            "잔치집 식혜": {"price": 1000, "stock": 10}
        }
        
        # 동전 보유량 초기화
        self.coins = {
            1000: 10,  # 1000원 지폐
            500: 20,   # 500원 동전
            100: 30,   # 100원 동전
            50: 30     # 50원 동전
        }
        
        # 관리자 비밀번호
        self.admin_password = "1234"
        
        # 현재 투입된 금액
        self.inserted_money = 0

    def check_change(self, amount):
        """거스름돈을 낼 수 있는지 확인하는 함수"""
        remaining = amount
        
        # 1000원 계산
        cnt_1000 = min(remaining // 1000, self.coins[1000])
        remaining -= cnt_1000 * 1000
        
        # 500원 계산
        cnt_500 = min(remaining // 500, self.coins[500])
        remaining -= cnt_500 * 500
        
        # 100원 계산
        cnt_100 = min(remaining // 100, self.coins[100])
        remaining -= cnt_100 * 100
        
        # 50원 계산
        cnt_50 = min(remaining // 50, self.coins[50])
        remaining -= cnt_50 * 50
        
        return remaining == 0
    
    def update_stock(self, name, amount):
        """특정 음료의 재고를 새 값으로 설정하는 함수"""
        if name in self.drinks:
            self.drinks[name]["stock"] = amount
            return True
        return False

    def get_drink_price(self, name):
        """선택한 음료의 가격을 반환하는 함수"""
        if name in self.drinks:
            return self.drinks[name]["price"]
        return 0
