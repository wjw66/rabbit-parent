package com.wjw.api;

import com.wjw.exception.MessageRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : wjwjava01@163.com
 * @date : 0:28 2020/8/11
 * @description :
 */
public class MessageBuilder {
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
    private Map<String, Object> attributes = new HashMap<>(16);
    /**
     * 延时消息的时间参数
     */
    private int delayMills;
    /**
     * 消息的类型,默认为CONFIRM(确认消息)
     */
    private String messageType = MessageType.CONFIRM;

    private MessageBuilder() {
    }

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withDelayMills(int delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder withMessageId(String attribute, Object object) {
        this.attributes.put(attribute, object);
        return this;
    }

    public MessageBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() {
        if (StringUtils.isBlank(messageId)) {
            messageId = UUID.randomUUID().toString();
        }

        if (StringUtils.isBlank(topic)) {
            throw new MessageRuntimeException("topic cannot  be null");
        }

        return new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
    }
}
