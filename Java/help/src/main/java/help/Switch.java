package help;

import java.util.Scanner;

public class Switch {
    public static void main(String[] args) {
        //switch
//        Scanner scanner = new Scanner(System.in);// 사용자로부터 월을 입력받습니다.
//        System.out.print("월을 입력하세요 (1-12): ");
//        int month = scanner.nextInt();
//
//        // 입력된 월에 따라 해당하는 월의 일 수를 출력합니다.
//        switch (month) { // month = 5
//            case 1:
//                System.out.println("case 1");
//            case 3:
//                System.out.println("case 3");
//            case 5:
//                System.out.println("case 5");
//            case 7:
//                System.out.println("case 7");
//            case 8:
//                System.out.println("case 8");
//            case 10:
//                System.out.println("case 10");
//            case 12:
//                System.out.println("case 12 break문 있음");
//                break;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                System.out.println(month + "월은 30일까지 있습니다.");
//                break;  //for문 while문에서도 써요
//            case 2:
//                System.out.println(month + "월은 28 또는 29일까지 있습니다.");
//                break;
//            default: // if 문의 else 같은 느낌
//                System.out.println("default 실행");
//        }
//
//        scanner.close();

        int c = 1;
        while (c<=10){
            if (c==5){
                c += 1;
                continue;
            }
            System.out.println("c:" + c);
            c += 1; // c = c + 1
        }
    }
}
