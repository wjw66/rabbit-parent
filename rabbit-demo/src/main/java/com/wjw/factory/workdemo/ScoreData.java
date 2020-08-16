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
    private Integer score1;
    private Integer score2;
    private Integer score3;

    public ScoreData() {
    }

    public ScoreData(Integer indexId, String indexName, Integer weight) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.weight = weight;
    }

    public ScoreData(Integer indexId, String indexName, Integer weight, Integer score1, Integer score2, Integer score3) {
        this.indexId = indexId;
        this.indexName = indexName;
        this.weight = weight;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Integer getScore3() {
        return score3;
    }

    public void setScore3(Integer score3) {
        this.score3 = score3;
    }
}
