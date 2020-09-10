package com.wjw.parser;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.wjw.annotation.ElasticJobConfig;
import com.wjw.enums.ElasticJobTypeEnum;
import com.wjw.task.autoconfig.JobZookeeperProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import java.util.Iterator;
import java.util.Map;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 15:13
 * @description : 解析ElasticJobConfig注解,在所有的bean都加载完后装配这个类
 **/
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
            for (Iterator<?> it = beanMap.values().iterator(); it.hasNext(); ) {
                Object configBean = it.next();
                Class<?> beanClass = configBean.getClass();
                //如果类名中包含$，分割保留前面的（因为有些类包含匿名类）
                if (beanClass.getName().indexOf("$") > 0) {
                    String className = beanClass.getName();
                    beanClass = Class.forName(className.substring(0, className.indexOf("$")));
                }
                //获取接口类型  用于判断是什么类型的任务
                Class<?>[] interfaces = beanClass.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    ElasticJobTypeEnum jobTypeName = ElasticJobTypeEnum.getJobType(anInterface.getSimpleName());
                }
                String simpleName = beanClass.getInterfaces().getClass().getSimpleName();
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

            }
        } catch (Exception e) {

        }

    }
}
