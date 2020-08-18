package com.wjw.serializer;

/**
 * @author : wjwjava01@163.com
 * @date : 21:28 2020/8/17
 * @description :
 */
public interface SerializerFactory {
    /**
     * 创建序列化实现
     * @return
     */
    Serializer create();
}
