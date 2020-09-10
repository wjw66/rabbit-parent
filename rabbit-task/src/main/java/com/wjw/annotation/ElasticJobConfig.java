package com.wjw.annotation;

import java.lang.annotation.*;

/**
 * @author : wangjianwei
 * @version : 1.0
 * @date : 2020/9/10 14:51
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticJobConfig {
    /**
     * 作业名称
     *
     * @return
     */
    String name();

    /**
     * cron表达式，用于控制作业触发时间
     *
     * @return
     */
    String cron() default "";

    /**
     * 作业分片总数
     *
     * @return
     */
    int shardingTotalCount() default 1;

    /**
     * 分片序列号和参数用等号分隔，多个键值对用逗号分隔
     * 分片序列号从0开始，不可大于或等于作业分片总数
     * 如：
     * 0=a,1=b,2=c
     *
     * @return
     */
    String shardingItemParameters() default "";

    /**
     * 作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业
     * 例：每次获取的数据量、作业实例从数据库读取的主键等
     *
     * @return
     */
    String jobParameter() default "";

    /**
     * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     *
     * @return
     */
    boolean failover() default false;

    /**
     * 是否开启错过任务重新执行
     *
     * @return
     */
    boolean misfire() default true;

    /**
     * 作业描述信息
     *
     * @return
     */
    String description() default "";

    /**
     * 本地配置是否可覆盖注册中心配置
     * 如果可覆盖，每次启动作业都以本地配置为准
     *
     * @return
     */
    boolean overwrite() default false;

    /**
     * 是否流式处理数据
     * 如果流式处理数据, 则fetchData不返回空结果将持续执行作业
     * 如果非流式处理数据, 则处理数据完成后作业结束
     *
     * @return
     */
    boolean streamingProcess() default false;

    /**
     * 脚本型作业执行命令行
     *
     * @return
     */
    String scriptCommandLine() default "";

    /**
     * 监控作业运行时状态
     * 每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。因为是瞬时状态，所以无必要监控。
     * 请用户自行增加数据堆积监控。并且不能保证数据重复选取，应在作业中实现幂等性。
     * 每次作业执行时间和间隔时间均较长的情况，建议监控作业运行时状态，可保证数据不会重复选取。
     *
     * @return
     */
    boolean monitorExecution() default false;

    /**
     * 作业监控端口
     * 建议配置作业监控端口, 方便开发者dump作业信息。
     * 使用方法: echo “dump” | nc 127.0.0.1 9888
     *
     * @return
     */
    public int monitorPort() default -1;    //must

    /**
     * 最大允许的本机与注册中心的时间误差秒数
     * 如果时间误差超过配置秒数则作业启动时将抛异常
     * 配置为-1表示不校验时间误差
     *
     * @return
     */
    public int maxTimeDiffSeconds() default -1;    //must

    /**
     * 作业分片策略实现类全路径
     * 默认使用平均分配策略
     *
     * @return
     */
    public String jobShardingStrategyClass() default "";    //must

    /**
     * 修复作业服务器不一致状态服务调度间隔时间，配置为小于1的任意值表示不执行修复
     * 单位：分钟
     *
     * @return
     */
    public int reconcileIntervalMinutes() default 10;    //must

    /**
     * 作业事件追踪的数据源Bean引用
     *
     * @return
     */
    public String eventTraceRdbDataSource() default "";    //must

    /**
     * @return
     */
    public String listener() default "";    //must

    /**
     * 作业是否禁止启动
     * 可用于部署作业时，先禁止启动，部署结束后统一启动
     *
     * @return
     */
    public boolean disabled() default false;    //must

    public String distributedListener() default "";

    /**
     * 最后一个作业执行前的执行方法的超时时间
     * 单位：毫秒
     *
     * @return
     */
    public long startedTimeoutMilliseconds() default Long.MAX_VALUE;    //must

    /**
     * 最后一个作业执行后的执行方法的超时时间
     * 单位：毫秒
     *
     * @return
     */
    public long completedTimeoutMilliseconds() default Long.MAX_VALUE;        //must

    /**
     * 扩展异常处理类
     *
     * @return
     */
    public String jobExceptionHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultJobExceptionHandler";

    /**
     * 扩展作业处理线程池类
     *
     * @return
     */
    public String executorServiceHandler() default "com.dangdang.ddframe.job.executor.handler.impl.DefaultExecutorServiceHandler";
}
