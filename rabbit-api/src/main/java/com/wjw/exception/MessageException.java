package com.wjw.exception;

/**
 * @author : wjwjava01@163.com
 * @date : 0:23 2020/8/11
 * @description :
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = -2740125272119437348L;

    public MessageException(){
        super();
    }

    public MessageException(String msg){
        super(msg);
    }

    public MessageException(String msg,Throwable cause){
        super(msg,cause);
    }
    public MessageException(Throwable cause){
        super(cause);
    }
}
