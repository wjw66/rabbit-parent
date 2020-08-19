package com.wjw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : wjwjava01@163.com
 * @date : 21:58 2020/8/19
 * @description : 自定义接口幂等注解,检测token
 */
//作用于方法上
@Target(ElementType.METHOD)
//保存到运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
}
