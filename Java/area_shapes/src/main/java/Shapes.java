import java.util.ArrayList;
import java.util.Scanner;

public class Shapes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num_of_shapes;
        ArrayList answers = new ArrayList();
        num_of_shapes = sc.nextInt();
        for (int i = 0; i < num_of_shapes; i++) {
            String shape = sc.next();
            int length  = sc.nextInt();
            answers.add(getArea(shape, length));
        }
        answers.forEach((answer) ->{
            System.out.println(answer);
        });
    }

    private static double getArea(String shape, int length) {
        double result;
        if (shape.equals("E")){
            result = Math.pow(length, 2) * Math.sqrt(3) / 4;
            return Math.round(result * 100) / 100.0;
        }else if(shape.equals("S")) {
            result = Math.pow(length, 2);
            return Math.round(result * 100) / 100.0;
        }else if(shape.equals("C")){
            result = 2 * Math.PI * length;
            return Math.round(result * 100) / 100.0;
        }
        return -1;
    }
}