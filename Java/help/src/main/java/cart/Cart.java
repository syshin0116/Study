package cart;

import java.util.Arrays;

public class Cart {

    String[] cart = new String[1];
    int money; // 금액
    int i = 0; //cart의 순서

    int tv;
    int com;
    int radio;

    void buy(String product) {
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

    void add(String product) {
        int idx = cart.length;
        for (int i = 0; i < cart.length; i++) {
            if (cart[i] == null) {
                cart[i] = product;
                info();
                return;
            }
        }
        String[] newCart = Arrays.copyOf(cart, cart.length*2);
        cart = newCart;
        cart[idx] = product;
        info();
    }

    void info() {
        for (String element : cart){
            if (element!=null) {
                System.out.println(element);
            }
        }
    }

}
