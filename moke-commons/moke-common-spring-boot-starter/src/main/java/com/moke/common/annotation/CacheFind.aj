package com.moke.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //注解对方法有效
@Retention(RetentionPolicy.RUNTIME)  //运行期有效
public @interface CacheFind {
     String preKey();          //用户标识key的前缀.
     int seconds() default 0;  //如果用户不写表示不需要超时. 如果写了以用户为准.
}
