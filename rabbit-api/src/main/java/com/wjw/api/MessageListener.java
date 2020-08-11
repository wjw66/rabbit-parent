package com.wjw.api;

/**
 * @author : wjwjava01@163.com
 * @date : 23:00 2020/8/11
 * @description : 消费者监听消息
 */
public interface MessageListener {
    /**
     * 消费者监听消息
     * @param message 消息
     */
    void onMessage(Message message);
}
