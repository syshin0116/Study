package help;

import java.util.ArrayList;
import java.util.Scanner;

public class air_pollution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> air_pollution = new ArrayList();
        int alert_count = 0;
        int num = sc.nextInt();
        for (int i = 0; i < num; i++) {
            air_pollution.add(sc.nextInt());
        }
        if(air_pollution.get(0) >= 100){
            alert_count++;
        }
        for (int i=0; i<air_pollution.size()-1; i++){
            if (air_pollution.get(i)+air_pollution.get(i+1) >= 100){
                alert_count++;
            }
        }
        System.out.println(alert_count);
    }
}
