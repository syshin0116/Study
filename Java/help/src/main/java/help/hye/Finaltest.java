package help.hye;

public class Finaltest {
    final String test;
    public Finaltest(String test){

        this.test = test;
    }

    public static void main(String[] args) {
        Finaltest finaltest = new Finaltest("생성자에서 받은 파이널");
        System.out.println("test:"+finaltest.test);
    }
}
