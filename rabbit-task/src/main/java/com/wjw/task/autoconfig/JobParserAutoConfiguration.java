package com.wjw.task.autoconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 11:30
 **/
@Slf4j
@Configuration
//表示配置文件中有 xx 前缀和 xx 属性才会加载配置（默认会自动加载）
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"}, matchIfMissing = false)
//@EnableConfigurationProperties(JobZookeeperProperties.class)
public class JobParserAutoConfiguration {
}
