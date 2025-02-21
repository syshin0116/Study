package help;

public class SystemTimeExample {
    public static void main(String[] args) {
        long time1 = System.nanoTime();
        int sum = 0;
        for(int i=0; i<1000000; i++){
            sum+=i;
        }
        long time2 = System.nanoTime();

        System.out.println("1000000까지의 합: " + sum);
        System.out.println("나노초: " + (time2-time1));
    }
}