package com.wjw.exception;

/**
 * @author : wjwjava01@163.com
 * @date : 21:49 2020/8/19
 * @description :
 */
public class IdempotentException extends Exception{
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public IdempotentException(String message) {
        super(message);
    }
}
