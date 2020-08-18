package com.wjw.serializer.impl;

import com.wjw.api.Message;
import com.wjw.serializer.Serializer;
import com.wjw.serializer.SerializerFactory;

/**
 * @author : wjwjava01@163.com
 * @date : 21:39 2020/8/17
 * @description :
 */
public class JacksonSerializerFactory implements SerializerFactory {
    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }
}
