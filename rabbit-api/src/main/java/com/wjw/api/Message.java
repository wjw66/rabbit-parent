package com.wjw.api;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wjwjava01@163.com
 * @date : 23:39 2020/8/10
 * @description : 消息实体类
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -5369893716983231338L;
    /**
     * 消息唯一ID
     */
    private String messageId;
    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息的路由规则
     */
    private String routingKey = "";
    /**
     * 消息的附加属性
     */
    private Map<String,Object> attributes = new HashMap<>(16);
    /**
     * 延时消息的时间参数
     */
    private int delayMills;
    /**
     * 消息的类型,默认为CONFIRM(确认消息)
     */
    private String messageType = MessageType.CONFIRM;

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills, String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }
}
