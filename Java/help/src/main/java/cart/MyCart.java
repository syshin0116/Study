package cart;

public class MyCart extends Cart {

    //1. 생성자는 money를 전달받고 money값 초기화, tv = 300, com = 400, radio = 500;

    //2. buy(), add(), info() 메서드 오버라이드
    /*
     * 상품명 "tv", "com", "radio"
     *
     * buy(상품명) 기능
     * 매개변수가 tv, com, radio라면 금액에서 상품가격을 차감하고, add() 메서드를 실행합니다.
     * 단, 올바른 상품이 아니거나, 금액이 부족할 경우에는 적절한 처리를 해주세요
     *
     */

    /*
     * add(상품명) 기능
     * 매개변수로 넘어온 상품을 cart배열에 순서대로 담아줍니다.
     * 단, cart배열이 꽉 찼다면, 2배 크기의 카트 배열로 옮겨주세요.
     *
     * 마지막에는 info() 메서드를 호출합니다.
     *
     */


    /*
     * info()는
     * cart배열에 담긴 물건 목록만 (null 제외) 출력해주세요.
     *
     */



    void money (int tv, int com, int radio) {
        this.tv = 300;
        this.com = 400;
        this.radio = 500;

    }
    public void buy(String product) {
        if (product.equals("tv")) {
            if (money >= tv) {
                money -= tv;
                add("tv");
            } else {
                System.out.println("금액이 부족합니다.");
            }
        } else if (product.equals("com")) {
            if (money >= com) {
                money -= com;
                add("com");
            } else {
                System.out.println("금액이 부족합니다.");
            }
        } else if (product.equals("radio")) {
            if (money >= radio) {
                money -= radio;
                add("radio");
            } else {
                System.out.println("금액이 부족합니다.");
            }
        } else {
            System.out.println("잘못된 상품명입니다.");
        }
    }
    public MyCart(int money) {
        this.money = money;
        money(tv, com, radio);
        System.out.println("money: "+this.money);
        System.out.println("tv: "+this.tv);
        System.out.println("com: "+this.com);
        System.out.println("radio: "+this.radio);
    }

    public static void main(String[] args) {
        MyCart mycart = new MyCart(100);

    }
}