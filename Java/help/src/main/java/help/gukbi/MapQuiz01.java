package help.gukbi;

import java.util.*;
import java.util.Map.Entry;

public class MapQuiz01 {

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);

        //맵을 이용하는데 key : 메뉴이름  value: 가격
        Map<String, Integer> map = new HashMap<>();

        while(true) {
            System.out.println("-----음식 메뉴 관리------");
            System.out.println("1. 신규 메뉴 등록"); //이미 등록된 메뉴인지 확인 후에 메뉴 등록
            System.out.println("2. 메뉴판 전체 보기"); //메뉴와 가격을 전부 출력
            System.out.println("3. 메뉴판 수정"); //변경할 메뉴를 받아서 메뉴가 있다면 가격을 수정
            System.out.println("4. 메뉴판 삭제"); //변경할 메뉴를 받아서 메뉴가 있다면 삭제
            System.out.println("5. 프로그램 종료"); //종료

            System.out.print("메뉴입력>");
            int menu = scan.nextInt();`

            if(menu == 1) {
                System.out.print("신규 메뉴명:");
                String name = scan.next();
                System.out.print("신규 메뉴 가격:");
                int price = scan.nextInt();
                if (map.containsKey(name)){
                    System.out.println("이미 등록된 메뉴입니다.");
                    continue;
                }
                map.put(name, price);
                System.out.println(name+"가 메뉴에 등록되었습니다.");
                System.out.println(map);

            } else if(menu == 2) { //메뉴판 전체 보기
                for (String key: map.keySet()){
                    System.out.println("메뉴:"+ key + "\t가격:"+map.get(key));
                }
            } else if(menu == 3) {// 메뉴판 수정
                System.out.print("수정할 메뉴명:");
                String name = scan.next();
                if (!map.containsKey(name)){
                    System.out.println("존재하지 않는 메뉴입니다.");
                    continue;
                }
                System.out.print("수정할 메뉴 가격:");
                int price = scan.nextInt();
                map.put(name, price);
                System.out.println(name+"의 가격이 "+price+"로 수정되었습니다.");

            } else if(menu == 4) { //삭제
                System.out.print("삭제할 메뉴명:");
                String name = scan.next();
                if (!map.containsKey(name)){
                    System.out.println("존재하지 않는 메뉴입니다.");
                    continue;
                }
                map.remove(name);
                System.out.println(name+"가 메뉴에서 삭제되었습니다.");
            } else if(menu == 5) {
                System.out.println("프로그램을 종료합니다.");
            }else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}