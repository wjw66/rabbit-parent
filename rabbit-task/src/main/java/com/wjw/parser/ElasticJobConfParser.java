package com.wjw.parser;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.wjw.annotation.ElasticJobConfig;
import com.wjw.enums.ElasticJobTypeEnum;
import com.wjw.task.autoconfig.JobZookeeperProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Map;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 15:13
 * @description : 解析ElasticJobConfig注解,在所有的bean都加载完后装配这个类
 **/
@Slf4j
public class ElasticJobConfParser implements ApplicationListener<ApplicationReadyEvent> {

    private JobZookeeperProperties jobZookeeperProperties;
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    public ElasticJobConfParser(JobZookeeperProperties jobZookeeperProperties, ZookeeperRegistryCenter zookeeperRegistryCenter) {
        this.jobZookeeperProperties = jobZookeeperProperties;
        this.zookeeperRegistryCenter = zookeeperRegistryCenter;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(ElasticJobConfig.class);
        try {
            for (Object configBean : beanMap.values()) {
                Class<?> beanClass = configBean.getClass();
                //如果类名中包含$，分割保留前面的（因为有些类包含匿名类）
                if (beanClass.getName().indexOf("$") > 0) {
                    String className = beanClass.getName();
                    beanClass = Class.forName(className.substring(0, className.indexOf("$")));
                }
                //获取接口类型  用于判断是什么类型的任务
                Class<?>[] interfaces = beanClass.getInterfaces();
                ElasticJobTypeEnum jobTypeName = null;
                for (Class<?> anInterface : interfaces) {
                    jobTypeName = ElasticJobTypeEnum.getJobType(anInterface.getSimpleName());
                }
                //获取配置项ElasticJobConfig
                ElasticJobConfig conf = beanClass.getAnnotation(ElasticJobConfig.class);
                String jobClass = beanClass.getName();
                String jobName = this.jobZookeeperProperties.getNamespace() + "." + conf.name();

                String cron = conf.cron();
                String shardingItemParameters = conf.shardingItemParameters();
                String description = conf.description();
                String jobParameter = conf.jobParameter();
                String jobExceptionHandler = conf.jobExceptionHandler();
                String executorServiceHandler = conf.executorServiceHandler();

                String jobShardingStrategyClass = conf.jobShardingStrategyClass();
                String eventTraceRdbDataSource = conf.eventTraceRdbDataSource();
                String scriptCommandLine = conf.scriptCommandLine();

                boolean failover = conf.failover();
                boolean misfire = conf.misfire();
                boolean overwrite = conf.overwrite();
                boolean disabled = conf.disabled();
                boolean monitorExecution = conf.monitorExecution();
                boolean streamingProcess = conf.streamingProcess();

                int shardingTotalCount = conf.shardingTotalCount();
                int monitorPort = conf.monitorPort();
                int maxTimeDiffSeconds = conf.maxTimeDiffSeconds();
                int reconcileIntervalMinutes = conf.reconcileIntervalMinutes();

                //	先把当当网的esjob的相关configuration
                JobCoreConfiguration coreConfig = JobCoreConfiguration
                        .newBuilder(jobName, cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters)
                        .description(description)
                        .failover(failover)
                        .jobParameter(jobParameter)
                        .misfire(misfire)
                        .jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), jobExceptionHandler)
                        .jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), executorServiceHandler)
                        .build();

                // 根据类名决定创建什么样的任务
                assert jobTypeName != null;
                JobTypeConfiguration typeConfig = jobTypeName.createElasticJob(coreConfig, jobClass, streamingProcess, scriptCommandLine);

                // LiteJobConfiguration
                LiteJobConfiguration jobConfig = LiteJobConfiguration
                        .newBuilder(typeConfig)
                        .overwrite(overwrite)
                        .disabled(disabled)
                        .monitorPort(monitorPort)
                        .monitorExecution(monitorExecution)
                        .maxTimeDiffSeconds(maxTimeDiffSeconds)
                        .jobShardingStrategyClass(jobShardingStrategyClass)
                        .reconcileIntervalMinutes(reconcileIntervalMinutes)
                        .build();

                //创建一个Spring的beanDefinition
                BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringJobScheduler.class);
                factory.setInitMethodName("init");
                factory.setScope("prototype");
                //添加参数
                //1.添加Bean的构造参数，相当于添加自己的真实的任务实现类
                if (!ElasticJobTypeEnum.SCRIPT.getType().equals(jobTypeName.getType())) {
                    factory.addConstructorArgValue(configBean);
                }
                //2.添加注册中心
                factory.addConstructorArgValue(this.zookeeperRegistryCenter);
                //3.添加LiteJobConfiguration
                factory.addConstructorArgValue(jobConfig);
                //4.如果有eventTraceRdbDataSource则也添加
                if (StringUtils.isNotBlank(eventTraceRdbDataSource)) {
                    BeanDefinitionBuilder rdbFactory = BeanDefinitionBuilder.rootBeanDefinition(JobEventRdbConfiguration.class);
                    rdbFactory.addConstructorArgReference(eventTraceRdbDataSource);
                    factory.addConstructorArgValue(rdbFactory.getBeanDefinition());
                }
                //5.添加监听
                List<?> elasticJobListeners = getTargetElasticJobListeners(conf);
                factory.addConstructorArgValue(elasticJobListeners);

                //把factory，也就是SpringJobScheduler注入到Spring容器中
                DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
                String registerBeanName = conf.name() + "SpringJobScheduler";
                defaultListableBeanFactory.registerBeanDefinition(registerBeanName, factory.getBeanDefinition());
                SpringJobScheduler scheduler = (SpringJobScheduler) applicationContext.getBean(registerBeanName);
                scheduler.init();
                log.info("启动elastic-job作业: " + jobName);
            }
            log.info("共计启动elastic-job作业数量为: {} 个", beanMap.values().size());
        } catch (Exception e) {
            log.error("elasticjob 启动异常, 系统强制退出", e);
            System.exit(1);
        }

    }

    private List<BeanDefinition> getTargetElasticJobListeners(ElasticJobConfig conf) {
        List<BeanDefinition> result = new ManagedList<>(2);
        String listeners = conf.listener();
        if (StringUtils.isNotBlank(listeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(listeners);
            factory.setScope("prototype");
            result.add(factory.getBeanDefinition());
        }

        String distributedListeners = conf.distributedListener();
        long startedTimeoutMilliseconds = conf.startedTimeoutMilliseconds();
        long completedTimeoutMilliseconds = conf.completedTimeoutMilliseconds();

        if (StringUtils.isNotBlank(distributedListeners)) {
            BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(distributedListeners);
            factory.setScope("prototype");
            factory.addConstructorArgValue(startedTimeoutMilliseconds);
            factory.addConstructorArgValue(completedTimeoutMilliseconds);
            result.add(factory.getBeanDefinition());
        }
        return result;
    }
}
