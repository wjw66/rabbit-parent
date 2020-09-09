package com.wjw.factory.workdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 18:06 2020/8/16
 * @description :
 */
public class ReflactDemo {

    public static void main(String[] args) {
        List<User> users1 = new ArrayList<>();
        List<? extends User> users = new ArrayList<>();
        users1.add(new User(1,"11",0));
        users1.add(new Child());
        users = users1;
        for (User user : users) {
            System.out.println(user);
        }


        List<? super User> userss = new ArrayList<>();
        userss.add(new User(1,"11",0));
        userss.add(new Child());
        users = users1;
        for (User user : users) {
            System.out.println(user);
        }
    }
}
