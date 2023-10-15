package lotto;

import java.util.Scanner;

public class LottoSimApp {
    public static void main(String[] args) {
        // 1차 구매
        System.out.println(">> 1차 구매");
        LottoCard card1 = new LottoCard(2);
        card1.manual(0, 1, 11, 22, 33, 44, 47);
        card1.manual(1, 3, 9, 15, 17, 33, 45);
        card1.show();

        // 2차 구매
        System.out.println("\n>> 2차 구매");
        LottoCard card2 = new LottoCard(5);
        for (int i = 0; i < 5; i++) {
            card2.auto(i);
        }
        card2.show();
    }

}