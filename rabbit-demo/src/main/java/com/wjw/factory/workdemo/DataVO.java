package com.wjw.factory.workdemo;

import java.util.*;

/**
 * @author : wjwjava01@163.com
 * @date : 10:49 2020/8/16
 * @description :
 */
public class DataVO {
    private static final List<String> HEADERS = new ArrayList<>();

    public static void main(String[] args) {
        List<User> dataList = DataList.getDataList();
        dataList.forEach(System.out::println);
        //渲染表头
        //[{姓名: name},{年龄: age},item for in list {item.indexId ,item.indexName,item.weigh}
        HEADERS.add("工号");
        HEADERS.add("姓名");
        HEADERS.add("年龄");
        List<ScoreData> scoreDataList = dataList.get(0).getScoreDataList();

        scoreDataList.forEach(scoreData -> {
            HEADERS.add(scoreData.getIndexName());
            HEADERS.add(scoreData.getWeight().toString());
        });
        System.out.println(HEADERS);
        List<Map<String, Object>> list = new ArrayList<>();
        dataList.forEach(date -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("uid",date.getUid());
            map.put("name",date.getName());
            map.put("age",date.getAge());
            List<ScoreData> scoreDataS = date.getScoreDataList();
            for (int i = 0; i < scoreDataS.size(); i++) {
                ScoreData scoreData = scoreDataS.get(i);
                map.put("worseValue" + i ,scoreData.getScore1());
                map.put("completeValue" + i , scoreData.getScore2());
                map.put("aimValue" + i , scoreData.getScore3());
            }
            list.add(map);
        });

        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }
    }
}
