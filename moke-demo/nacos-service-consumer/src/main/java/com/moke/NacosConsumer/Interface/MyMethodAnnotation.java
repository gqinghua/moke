package com.moke.NacosConsumer.Interface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: sw
 * @ClassName MyMethodAnnotation
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-10-19 17:23
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMethodAnnotation {

    String uri();

    String desc();
}
