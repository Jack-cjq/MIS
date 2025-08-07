package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要获取当前用户信息的接口方法
 * 使用此注解的方法会自动从JWT Token中获取当前用户信息
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
    /**
     * 是否必需，默认为true
     * 如果为true，则用户必须登录且Token有效
     * 如果为false，则允许匿名访问，但会尝试获取用户信息
     */
    boolean required() default true;
} 