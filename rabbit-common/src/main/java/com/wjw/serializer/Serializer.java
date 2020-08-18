package com.wjw.serializer;

/**
 * @author : wjwjava01@163.com
 * @date : 21:29 2020/8/17
 * @description : 序列化和反序列化接口
 */
public interface Serializer {
    /**
     * 对象序列化为字节数组
     *
     * @param object 对象
     * @return 字节数组
     */
    byte[] serializeRaw(Object object);

    /**
     * 对象序列化为字符串
     *
     * @param object 对象
     * @return 字符串
     */
    String serialize(Object object);

    /**
     * 字符串反序列化为指定类型
     *
     * @param content 数据
     * @param <T>     泛型
     * @return 指定类型
     */
    <T> T deSerialize(String content);

    /**
     * 对象反序列化为指定类型
     *
     * @param content 数据
     * @param <T>     泛型
     * @return 指定类型
     */
    <T> T deSerialize(byte[] content);
}
