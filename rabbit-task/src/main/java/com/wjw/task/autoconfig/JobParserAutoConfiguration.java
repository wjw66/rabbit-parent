package com.wjw.task.autoconfig;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.wjw.parser.ElasticJobConfParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 11:30
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(JobZookeeperProperties.class)
//表示配置文件中有 xx 前缀和 xx 属性才会加载配置（默认会自动加载）
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"}, matchIfMissing = false)
public class JobParserAutoConfiguration {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(JobZookeeperProperties jobZookeeperProperties) {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(jobZookeeperProperties.getServerLists(),
                jobZookeeperProperties.getNamespace());
        zkConfig.setDigest(jobZookeeperProperties.getDigest());
        zkConfig.setBaseSleepTimeMilliseconds(jobZookeeperProperties.getBaseSleepTimeMilliseconds());
        zkConfig.setConnectionTimeoutMilliseconds(jobZookeeperProperties.getConnectionTimeoutMilliseconds());
        zkConfig.setMaxRetries(jobZookeeperProperties.getMaxRetries());
        zkConfig.setSessionTimeoutMilliseconds(jobZookeeperProperties.getSessionTimeoutMilliseconds());
        zkConfig.setMaxSleepTimeMilliseconds(jobZookeeperProperties.getMaxSleepTimeMilliseconds());

        log.info("初始化job 注册中心配置成功，配置为 zkConfig = {} ", zkConfig);
        return new ZookeeperRegistryCenter(zkConfig);
    }

    @Bean
    public ElasticJobConfParser elasticJobConfParser(JobZookeeperProperties jobZookeeperProperties,ZookeeperRegistryCenter zookeeperRegistryCenter){
        return new ElasticJobConfParser(jobZookeeperProperties,zookeeperRegistryCenter);
    }
}
