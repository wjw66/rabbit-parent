package com.wjw.broker;

import com.google.common.base.Preconditions;
import com.wjw.api.Message;
import com.wjw.api.MessageProducer;
import com.wjw.api.MessageType;
import com.wjw.api.SendCallback;
import com.wjw.exception.MessageRuntimeException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 23:02 2020/8/11
 * @description : 发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {
    @Resource
    private RabbitBroker rabbitBroker;

    /**
     * 发送消息
     *
     * @param message
     * @throws MessageRuntimeException
     */
    @Override
    public void send(Message message) throws MessageRuntimeException {
        //空数据检查
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    /**
     * 发送消息,附带回调,执行相应的业务逻辑
     *
     * @param message
     * @param sendCallback
     * @throws MessageRuntimeException
     */
    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRuntimeException {

    }

    /**
     * 批量发送消息
     *
     * @param message
     * @throws MessageRuntimeException
     */
    @Override
    public void send(List<Message> message) throws MessageRuntimeException {

    }
}
