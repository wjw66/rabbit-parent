package com.wjw.enums;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;

import java.util.Arrays;

/**
 * 定时任务的;类型
 *
 * @author 01392915
 */
public enum ElasticJobTypeEnum {
    //
    SIMPLE("SimpleJob", "简答类型job") {
        @Override
        public JobTypeConfiguration createElasticJob(JobCoreConfiguration jobCoreConfiguration, String jobClass, boolean streamingProcess, String scriptCommandLine) {
            return new SimpleJobConfiguration(jobCoreConfiguration, jobClass);
        }
    },
    DATAFLOW("DataflowJob", "流式类型job") {
        @Override
        public JobTypeConfiguration createElasticJob(JobCoreConfiguration jobCoreConfiguration, String jobClass, boolean streamingProcess, String scriptCommandLine) {
            return new DataflowJobConfiguration(jobCoreConfiguration, jobClass, streamingProcess);
        }
    },
    SCRIPT("ScriptJob", "脚本类型Job") {
        @Override
        public JobTypeConfiguration createElasticJob(JobCoreConfiguration jobCoreConfiguration, String jobClass, boolean streamingProcess, String scriptCommandLine) {
            return new ScriptJobConfiguration(jobCoreConfiguration, scriptCommandLine);
        }
    },
    ;
    private String type;
    private String desc;

    ElasticJobTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ElasticJobTypeEnum getJobType(String name) {
        return Arrays.stream(ElasticJobTypeEnum.values())
                .filter(type -> type.type.equals(name))
                .findFirst()
                .orElse(null);
    }

    public abstract JobTypeConfiguration createElasticJob(JobCoreConfiguration jobCoreConfiguration, String jobClass, boolean streamingProcess, String scriptCommandLine);

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
