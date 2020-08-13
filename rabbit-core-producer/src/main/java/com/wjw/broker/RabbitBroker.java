package com.wjw.broker;

import com.wjw.api.Message;

/**
 * @author : wjwjava01@163.com
 * @date : 4:42 2020/8/14
 * @description : 具体发送不同种类型的接口
 */
public interface RabbitBroker {
    /**
     * 发送迅速消息
     * @param message
     */
    void rapidSend(Message message);

    /**
     * 发送确认消息
     * @param message
     */
    void confirmSend(Message message);

    /**
     * 可靠性消息投递
     * @param message
     */
    void reliantSend(Message message);

    /**
     * 批量发送
     */
    void sendMessages();

}
