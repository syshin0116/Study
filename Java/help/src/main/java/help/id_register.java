package help;

import java.util.Scanner;

public class id_register {
    /*
     * 스캐너 사용해서 id를 입력받습니다.
     * 아이디는 공백을 포함해서 받을 수 있습니다. nextLine()
     *
     * 단, 공백을 제거한 아이디가 5글자 미만이면 다시 입력받습니다.
     *       5글자 이상이라면 "id가 등록되었습니다" 출력후 종료
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("ID를 입력하세요>");
            String str = sc.nextLine();
            String id = str.replace(" ", "");
            if (id.length() > 5) {
                System.out.println("id가 등록되었습니다.");
                return;
            }
        }
    }
}
