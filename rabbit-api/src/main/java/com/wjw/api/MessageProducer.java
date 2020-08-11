package com.wjw.api;

import com.wjw.exception.MessageRuntimeException;

import java.util.List;

/**
 * @author : wjwjava01@163.com
 * @date : 22:04 2020/8/11
 * @description :
 */
public interface MessageProducer {
    /**
     * 发送消息
     *
     * @param message
     * @throws MessageRuntimeException
     */
    void send(Message message) throws MessageRuntimeException;

    /**
     * 发送消息,附带回调,执行相应的业务逻辑
     *
     * @param message
     * @param sendCallback
     * @throws MessageRuntimeException
     */
    void send(Message message, SendCallback sendCallback) throws MessageRuntimeException;

    /**
     * 批量发送消息
     *
     * @param message
     * @throws MessageRuntimeException
     */
    void send(List<Message> message) throws MessageRuntimeException;
}
