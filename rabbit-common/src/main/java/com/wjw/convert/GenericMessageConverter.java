package com.wjw.convert;

import com.google.common.base.Preconditions;
import com.wjw.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author : wjwjava01@163.com
 * @date : 22:04 2020/8/17
 * @description :
 */
public class GenericMessageConverter implements MessageConverter {
    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    /**
     * 将amqp的Message转换成自定义的message
     * @param message amqp的message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.serializer.deSerialize(message.getBody());
    }

    /**
     * 将自己的message对象反序列化成amqp的message
     * @param o
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(this.serializer.serializeRaw(o),messageProperties);
    }

}