package com.wjw.broker;

import com.google.common.base.Preconditions;
import com.wjw.api.Message;
import com.wjw.api.MessageType;
import com.wjw.exception.MessageRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : wjwjava01@163.com
 * @date : 22:34 2020/8/15
 * @description : RabbitTemplate的池化封装，解决高并发下单例template效率低的问题
 * 1.每个topic对应一个RabbitTemplate，提高效率
 * 2.根据不同的topic不同，可定制化自己的Template（如添加事务等）
 */
@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    /**
     * 创建MQ连接池
     */
    private final Map<String, RabbitTemplate> rabbitMap = new ConcurrentHashMap<>();
    /**
     * 注入连接工厂
     */
    @Resource
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getRabbitTemplate(Message message) throws MessageRuntimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        //1.如果已存在rabbitTemplate，返回
        if (Objects.nonNull(rabbitTemplate)) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getRabbitTemplate# topic:{} 不存在", topic);

        //2.不存在则创建新的
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setExchange(topic);
        template.setRoutingKey(message.getRoutingKey());
        //重试
        template.setRetryTemplate(new RetryTemplate());
        //序列化（重要）
//        template.setMessageConverter();

        String messageType = message.getMessageType();
        //如果不是迅速消息，则需要添加回调函数
        if (!MessageType.RAPID.equals(messageType)) {
            template.setConfirmCallback(this);
        }
        //不存在则写入（新值不覆盖旧值）
        rabbitMap.putIfAbsent(topic, template);

        //为什么不直接返回template?因为 rabbitMap 不会替换旧值
        return rabbitMap.get(topic);
    }

    /**
     * 具体的应答
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        assert correlationData != null;
        String[] strings = Objects.requireNonNull(correlationData.getId()).split("#");
        String messageId = strings[0];
        long sendTime = Long.parseLong(strings[1]);

        //如果ack了，TODO 后续完善
        if (ack) {
            log.info("发送消息成功, messageId : {} ,sendTime : {}", messageId, sendTime);
        } else {
            log.error("发送消息错误, messageId : {} ,sendTime : {}", messageId, sendTime);
        }
    }
}
