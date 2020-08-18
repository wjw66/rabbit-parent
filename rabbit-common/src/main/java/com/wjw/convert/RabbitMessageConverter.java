package com.wjw.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author : wjwjava01@163.com
 * @date : 22:26 2020/8/17
 * @description : 使用装饰者模式（或者叫代理）来进一步的修饰converter
 */
public class RabbitMessageConverter implements MessageConverter {
    /**
     * 设置默认过期时间为24小时
     */
    private final String defaultExpire = String.valueOf(24 * 60 * 60 * 1000);
    private GenericMessageConverter delegate;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        this.delegate = genericMessageConverter;
    }

    /**
     * 将自己的message对象反序列化成amqp的message
     *
     * @param o
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        //对自己的消息进行装饰，设置消息默认的过期时间
        messageProperties.setExpiration(defaultExpire);
        return this.delegate.toMessage(o, messageProperties);
    }

    /**
     * 将amqp的Message转换成自定义的message
     *
     * @param message amqp的message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.delegate.fromMessage(message);
    }
}
