package com.wjw.factory.workdemo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : wjwjava01@163.com
 * @date : 10:49 2020/8/16
 * @description :
 */
public class DataVO {
    private static final List<String> HEADERS = new ArrayList<>();

    public static void main(String[] args) throws IllegalAccessException {
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
//            List<ScoreData> scoreDataS = date.getScoreDataList();
//            for (int i = 0; i < scoreDataS.size(); i++) {
//                ScoreData scoreData = scoreDataS.get(i);
//            }
            getFiledValue(date, map, null);
            list.add(map);
            String[] filedName = getFiledName(date);

        });

        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }

    }

    /**
     * 获取属性名数组
     *
     * @return
     */
    public static <T> String[] getFiledName(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fieldNames.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取类所有属性值,并封装到map中
     *
     * @return
     */
    public static void getFiledValue(Object obj, Map<String, Object> map, Integer num) {
        //获取类属性
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();

                //如果类中包含list，将list展开放入map
                if (field.getType().equals(List.class)) {
                    List<?> list = (List<?>) field.get(obj);
                    for (int i = 0; i < list.size(); i++) {
                        Object o = list.get(i);
                        getFiledValue(o, map, i);
                    }
                    break;
                }

                if (map.containsKey(fieldName)) {
                    map.put(fieldName + num, field.get(obj));
                } else {
                    map.put(fieldName, field.get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
