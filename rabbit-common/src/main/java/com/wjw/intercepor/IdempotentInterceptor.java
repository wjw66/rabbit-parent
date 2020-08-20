package com.wjw.intercepor;

import com.wjw.annotation.AutoIdempotent;
import com.wjw.exception.IdempotentException;
import com.wjw.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author : wjwjava01@163.com
 * @date : 22:02 2020/8/19
 * @description : 自定义主解的拦截器
 */
@Slf4j
@Component
public class IdempotentInterceptor implements HandlerInterceptor {
    @Resource
    private TokenService tokenService;

    /**
     * 在方法执行前进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return 返回true方行, 返回false阻止
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.如果传入的handler不是HandlerMethod的实例,直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        //2.获取方法上的注解
        AutoIdempotent annotation = method.getAnnotation(AutoIdempotent.class);
        //如果方法上有这个注解
        if (Objects.nonNull(annotation)) {
            try {
                return tokenService.checkToken(request);
            } catch (IdempotentException e) {
                log.error("重复校验", e);
                throw e;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
