package help;

import java.util.Scanner;

public class Diablo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int currentLevel = scanner.nextInt(); // 현재레벨
        int targetLevel = scanner.nextInt(); // 목표레벨

        int totalRequiredXp = (int)(Math.pow(2, targetLevel-1) - Math.pow(2, currentLevel-1))*100;

//
//        for (int level = currentLevel; level < targetLevel; level++) {
//            int requiredXp = (int) (Math.pow(2, level - 1) * 100);
//            totalRequiredXp += requiredXp;
//        }

        System.out.println(totalRequiredXp);

        scanner.close();
    }
}