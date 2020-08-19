package com.wjw.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : wjwjava01@163.com
 * @date : 23:16 2020/8/19
 * @description : 全局异常处理
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler
    public Object idempotentException(IdempotentException e) {
        return e.getMessage();
    }
}
