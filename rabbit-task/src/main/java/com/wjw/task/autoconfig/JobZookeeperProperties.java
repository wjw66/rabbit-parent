package com.wjw.task.autoconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 11:40
 * @description : 读取zk的配置
 **/
@Data
@ConfigurationProperties(prefix = "elastic.job.zk")
public class JobZookeeperProperties {
    /**
     * Zookeeper的命名空间
     */
    private String namespace;
    /**
     * 连接Zookeeper服务器的列表
     * 包括IP地址和端口号
     * 多个地址用逗号分隔
     * 如: host1:2181,host2:2181
     */
    private String serverLists;
    /**
     * 最大重试次数
     */
    private int maxRetries = 3;
    /**
     * 连接超时时间
     * 单位：毫秒
     */
    private int connectionTimeoutMilliseconds = 15000;
    /**
     * 会话超时时间
     * 单位：毫秒
     */
    private int sessionTimeoutMilliseconds = 60000;
    /**
     * 等待重试的间隔时间的初始值
     * 单位：毫秒
     */
    private int baseSleepTimeMilliseconds = 1000;
    /**
     * 等待重试的间隔时间的最大值
     * 单位：毫秒
     */
    private int maxSleepTimeMilliseconds = 3000;
    /**
     * 连接Zookeeper的权限令牌
     * 缺省为不需要权限验证
     */
    private String digest = "";
}
