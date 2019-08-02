package com.xiaowei.eventbuss;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解
 */
@Target(ElementType.METHOD)//声明注解的作用域,注解放在什么上面
@Retention(RetentionPolicy.RUNTIME)//声明注解的生命周期
public @interface Subscibe {
    ThreadMode threadMode() default ThreadMode.POSTING;
}
