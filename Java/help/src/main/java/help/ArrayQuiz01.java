package help;

import java.util.Arrays;

public class ArrayQuiz01 {
    public static String[] test(String[] arr, String[] arr2) {
         /**
          * arr에는 있지만 arr2에는 없는 요소 구하기, 이하 차집합이라 명시
          *
          * 1. arr크기의 빈 배열 temp 생성
          * 2. arr 요소마다 arr2에 존재하는지 확인
          *     존재 시: 다음 요소로 넘어감(break)
          *     존재하지 않을시: temp에 추가, count(차집합 요소 개수) 1 증가
          * 3. temp에 차집합 요소가 들어가 있지만 처음에 arr크기로 생성했기 때문에 null값 포함되있으므로, null값을 없애줘야함
          * 4. 새로운 배열 answer을 count 크기로 생성, temp의 null값이 아닌 요소들 answer에 추가
          * 5. answer 배열 리턴
          */
        int count = 0; // 두 array 의 차집합 요소 개수 세는 용도
        boolean flag = false; //
        String[] temp = new String[arr.length];;
        for (String word2:arr2){
            for (String word1: arr){
                if (word2.equals(word1)){
                    break;
                }
                flag = true;
            }
            if (flag){
                break;
            }
            temp[count] = word2;
            count++;
        }
        String[] answer = new String[count];
        for (int j = 0; j < count; j++){
            answer[j] = temp[j];
        }
        return answer;
    }
    public static void main(String[] args) {

        //arr과 arr2는 길이가 1차이가 납니다.
        //arr은 마라톤 참가자입니다.
        //arr2는 마라톤 완주자입니다.
        //완주하지 못한 사람을 리턴하는 메서드를 만드세요.

        String[] arr = {"홍길동", "홍길자", "이순신", "신사임당"};
        String[] arr2 = {"홍길동", "이순신", "신사임당"};
        System.out.println(Arrays.toString(test(arr, arr2)));
    }
}