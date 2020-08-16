package com.wjw.factory.workdemo;

import lombok.Data;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 9:54 2020/8/16
 * @description :
 */
@Data
public class User {
    private Integer uid;
    private String name;
    private Integer age;
    private List<ScoreData> scoreDataList;


    public User() {
    }

    public User(Integer uid, String name, Integer age) {
        this.uid = uid;
        this.name = name;
        this.age = age;
    }

    public User(Integer uid, String name, Integer age, List<ScoreData> scoreDataList) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.scoreDataList = scoreDataList;
    }
}
