package com.wjw.api;

/**
 * @author : wjwjava01@163.com
 * @date : 22:06 2020/8/11
 * @description : 消息发送的确认回调
 */
public interface SendCallback {
    /**
     * 发送成功
     */
    void onSuccess();

    /**
     * 发送失败
     */
    void onFailure();
}
