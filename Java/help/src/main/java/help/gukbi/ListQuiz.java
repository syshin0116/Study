package help.gukbi;

import java.util.ArrayList;
import java.util.List;

public class ListQuiz {
    public static void main(String[] args) {
        /*
         * 1. list에 1~20까지 값을 순서대로 저장
         *
         * 2. list에 값을 순서대로 출력.
         *
         */
        List<Integer> list = new ArrayList<Integer>();
        for (int i=1;i<11;i++){
            list.add(i);
        }
        for (int data:list){
            System.out.println(data);
        }
    }
}
