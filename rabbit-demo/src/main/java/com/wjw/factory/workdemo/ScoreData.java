package com.wjw.factory.workdemo;

import lombok.Data;

/**
 * @author : wjwjava01@163.com
 * @date : 10:20 2020/8/16
 * @description :
 */
@Data
public class ScoreData {
    private Integer indexId;
    private String indexName;
    private Integer weight;
    private Integer worseValue;
    private Integer completeValue;
    private Integer challengeValue;

    public ScoreData() {
    }

    public ScoreData(Integer indexId, String indexName, Integer weight) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.weight = weight;
    }



    public Integer getIndexId() {
        return indexId;
    }

    public ScoreData(Integer indexId, String indexName, Integer weight, Integer worseValue, Integer completeValue, Integer challengeValue) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.weight = weight;
        this.worseValue = worseValue;
        this.completeValue = completeValue;
        this.challengeValue = challengeValue;
    }
}
