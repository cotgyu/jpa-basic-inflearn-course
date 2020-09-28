package hellojpa;

public class ValueMain {

    public static void main(String[] args) {

        int a = 10;
        int b = a;

        a = 20;


        // 20
        System.out.println("a:" + a);
        // 10
        System.out.println("b:" + b);


        // 같은 인스턴스 공유
        Integer a1 = new Integer(10);
        Integer b1 = a1;


    }
}
