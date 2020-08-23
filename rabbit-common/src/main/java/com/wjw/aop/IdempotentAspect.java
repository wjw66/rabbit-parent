package com.wjw.aop;

import com.wjw.exception.IdempotentException;
import com.wjw.token.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : wjwjava01@163.com
 * @date : 17:22 2020/8/23
 * @description :
 */
@Aspect
@Component
public class IdempotentAspect {
    @Autowired
    private TokenService tokenService;
    @Resource
    private HttpServletRequest request;

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.wjw.annotation.AutoIdempotent)")
    public void pointcut() {

    }

    /**
     * 配置前置通知
     *
     * @param joinpoint 切点
     */
    @Before("pointcut()")
    public void before(JoinPoint joinpoint) throws IdempotentException {
        tokenService.checkToken(request);
    }
}
