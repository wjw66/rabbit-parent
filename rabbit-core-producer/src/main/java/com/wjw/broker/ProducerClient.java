package com.wjw.broker;

import com.google.common.base.Preconditions;
import com.wjw.api.Message;
import com.wjw.api.MessageProducer;
import com.wjw.api.SendCallback;
import com.wjw.exception.MessageRuntimeException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 23:02 2020/8/11
 * @description : 发送消息的实际实现类
 */
@Component
public class ProducerClient implements MessageProducer {
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
