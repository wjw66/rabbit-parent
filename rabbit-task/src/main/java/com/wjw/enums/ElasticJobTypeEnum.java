package com.wjw.enums;

import java.util.Arrays;

/**
 * 定时任务的;类型
 *
 * @author 01392915
 */
public enum ElasticJobTypeEnum {
    //
    SIMPLE("SimpleJob", "简答类型job"),
    DATAFLOW("DataflowJob", "流式类型job"),
    SCRIPT("ScriptJob", "脚本类型Job"),
    ;
    private String type;
    private String desc;

    ElasticJobTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ElasticJobTypeEnum getJobType(String name){
        return Arrays.stream(ElasticJobTypeEnum.values())
                .filter(type -> type.type.equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
