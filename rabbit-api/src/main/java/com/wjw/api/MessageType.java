package com.wjw.api;

/**
 * @author : wjwjava01@163.com
 * @date : 23:54 2020/8/10
 * @description : 消息的类型
 */
public final class MessageType {
    /**
     * 迅速消息:不需要保障消息的可靠性,也不需要做confirm确认
     */
    public static final String RAPID = "0";

    /**
     * 确认消息:不需要保障消息的可靠性,但是会做confirm确认消息
     */
    public static final String CONFIRM = "1";

    /**
     * 可靠性消息:一定要保障消息的100%可靠性投递
     * ps:保障数据库和所发的消息是原子性的(最终一致的)
     */
    public static final String RELIANT = "2";
}
