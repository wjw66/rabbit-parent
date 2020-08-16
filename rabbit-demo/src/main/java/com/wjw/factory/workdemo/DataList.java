package com.wjw.factory.workdemo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 9:56 2020/8/16
 * @description :
 */
public class DataList {

    public static List<User> getDataList() {
        List<ScoreData> scores = Lists.newArrayList(
                new ScoreData(1001,"有责客诉率",50,72,22,44),
                new ScoreData(1002,"一线流失人数",10,32,22,76),
                new ScoreData(1003,"整改率",20,7,55,24),
                new ScoreData(1004,"希望率",20,80,33,68)
        );
        List<User> list = Lists.newArrayList(
                new User(1, "张三", 18,scores),
                new User(2, "李四", 19,scores));

        return list;
    }
}
