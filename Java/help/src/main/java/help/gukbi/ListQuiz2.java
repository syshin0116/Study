package help.gukbi;

import java.util.ArrayList;
import java.util.List;

public class ListQuiz2 {
    public static void main(String[] args) {
        /*
         * List2번
         * 1.User를 저장하는 list생성
         * 2.3개의 각각 다른 User객체를 만들고 순서대로 저장
         * 3.User객체 안에 홍길동이 있으면 해당 객체의 name, age를 출력하는 코드
         * 4.User객체 안에 홍길자가 있으면 해당 객체를 삭제 코드
         *
         */
        ArrayList<User> users = new ArrayList<User>();
        User user1 = new User("홍길동", "25");
        User user2 = new User("손흥민", "33");
        User user3 = new User("홍길자", "23");

        users.add(user1);
        users.add(user2);
        users.add(user3);
        for (User user: users){
            if (user.getName().equals("홍길동")){
                System.out.println("홍길동인 user 정보:\n"+"name:" + user.getName() + "\nage:" + user.getAge());
            }
        }
        users.removeIf(user -> user.getName().equals("홍길자"));

        System.out.println(users.size());
    }
}
