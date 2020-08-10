package com.wjw.exception;

/**
 * @author : wjwjava01@163.com
 * @date : 0:23 2020/8/11
 * @description :
 */
public class MessageRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2788540990099701770L;

    public MessageRuntimeException() {
        super();
    }

    public MessageRuntimeException(String msg) {
        super(msg);
    }

    public MessageRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MessageRuntimeException(Throwable cause) {
        super(cause);
    }
}
