package com.xxx.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Router API类 标识注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteHandler {

    String value() default "";

    boolean isOpen() default false;

    /**
     * 注册顺序，数字越大越先注册
     */
    int order() default 0;
}
