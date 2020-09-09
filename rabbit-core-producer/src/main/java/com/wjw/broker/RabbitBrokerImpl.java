package com.wjw.broker;

import com.wjw.api.Message;
import com.wjw.api.MessageType;
import com.wjw.constant.BrokerMessageConst;
import com.wjw.constant.BrokerMessageStatus;
import com.wjw.entity.BrokerMessage;
import com.wjw.service.MessageStoreServiceImpl;
import com.wjw.thread.AppThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author : wjwjava01@163.com
 * @date : 4:45 2020/8/14
 * @description :
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {
    @Resource
    private RabbitTemplateContainer rabbitTemplateContainer;
    @Resource
    private MessageStoreServiceImpl messageStoreService;

    /**
     * 发送迅速消息
     *
     * @param message
     */
    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
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
        message.setMessageType(MessageType.RELIANT);
        Date now = new Date();
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(message.getMessageId());
        brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
        //添加下次的重试时间
        brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
        brokerMessage.setCreateTime(now);
        brokerMessage.setUpdateTime(now);
        brokerMessage.setMessage(message);
        //消息入库
        messageStoreService.insert(brokerMessage);
        //消息发送
        sendKernel(message);

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
     * @param message 消息
     */
    private void sendKernel(Message message) {
        AppThreadPool.submit(() -> {
            CorrelationData correlationData =
                    //id + 时间戳
                    new CorrelationData(String.format("%s#%s", message.getMessageId(), System.currentTimeMillis()));

            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getRabbitTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            log.info("发送消息,消息id = {}", message.getMessageId());
        });
    }
}
