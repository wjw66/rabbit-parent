package com.wjw.config.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/9 14:58
 * @deprecated : 读取数据库连接资源
 **/

@Slf4j
@Configuration
@PropertySource("classpath:rabbit-producer-message.properties")
public class RabbitProducerDataSourceConfiguration {
    @Value("${rabbit.producer.druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "rabbitProducerDataSource")
    @Primary
    @ConfigurationProperties(prefix = "rabbit.producer.druid.jdbc")
    public DataSource rabbitProducerDataSource() throws SQLException {
        DataSource rabbitProducerDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("=====================  rabbitProducerDataSource : {} ========================", rabbitProducerDataSource);
        return rabbitProducerDataSource;
    }
}
