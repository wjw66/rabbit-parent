package com.wjw.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author : wjwjava01@163.com
 * @date : 22:56 2020/8/11
 * @description : 自动装配
 */
@Configuration
@ComponentScan({"com.wjw.*"})
public class RabbitProducerAutoConfiguration {

}
