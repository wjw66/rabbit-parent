package com.wjw.factory.simplefactory;

/**
 * @author : wjwjava01@163.com
 * @date : 20:20 2020/8/15
 * @description :
 */
public class InstanceofTest {
    //    private static String str = new String("111111");
    public static void main(String[] args) {
        String str = "1111";
        Integer num = 100;
        str(str);
        str(num);
    }

    private static void str(Object str) {
        boolean b = str instanceof String;

        System.out.println(b);

    }
}
