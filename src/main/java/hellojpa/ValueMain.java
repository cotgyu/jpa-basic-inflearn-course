package hellojpa;

import hellojpa.ex.Ex_Address;

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

        Ex_Address address1 = new Ex_Address("city1", "street1", "zip1");
        Ex_Address address2 = new Ex_Address("city1", "street1", "zip1");

        System.out.println("== :" + (address1 == address2));
        System.out.println("equals :" + (address1.equals(address2)));

    }
}
