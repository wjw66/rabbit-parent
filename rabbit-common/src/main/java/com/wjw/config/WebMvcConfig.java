package com.wjw.config;

import com.wjw.intercepor.IdempotentInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : wjwjava01@163.com
 * @date : 23:12 2020/8/19
 * @description :
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private IdempotentInterceptor idempotentInterceptor;

    /**
     * 将自定义拦截器注入到Spring中
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }
}
