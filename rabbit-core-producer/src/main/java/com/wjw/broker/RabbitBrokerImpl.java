package com.wjw.broker;

import com.wjw.api.Message;
import com.wjw.thread.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : wjwjava01@163.com
 * @date : 4:45 2020/8/14
 * @description :
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送迅速消息
     *
     * @param message
     */
    @Override
    public void rapidSend(Message message) {

    }

    /**
     * 发送确认消息
     *
     * @param message
     */
    @Override
    public void confirmSend(Message message) {

    }

    /**
     * 可靠性消息投递
     *
     * @param message
     */
    @Override
    public void reliantSend(Message message) {

    }

    /**
     * 批量发送
     */
    @Override
    public void sendMessages() {

    }

    /**
     * 发送消息的核心方法,使用异步线程池
     *
     * @param message
     */
    private void sendKernel(Message message) {
        ThreadPool.submit(() -> {
            CorrelationData correlationData =
                    //id + 时间戳
                    new CorrelationData(String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();

            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("发送消息,消息id = {}", message.getMessageId());
        });

    }
}
