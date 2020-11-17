package com.moke.common.aspect;

/**
 * @program: moke
 * @ClassName CacheFindAspect
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-11-11 15:30
 **/



import com.moke.common.annotation.CacheFind;
import com.moke.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


import java.util.Arrays;

@Aspect     //我是一个AOP切面类
@Component  //将类交给spring容器管理
public class CacheAOP {

    @Autowired
    private Jedis jedis;

    /**
     * 切面 = 切入点 + 通知方法
     *        注解相关 + 环绕通知  控制目标方法是否执行
     *
     *  难点:
     *      1.如何获取注解对象
     *      2.动态生成key  prekey + 用户参数数组
     *      3.如何获取方法的返回值类型
     */
    @Around("@annotation(cacheFind)")
    public Object around(ProceedingJoinPoint joinPoint, CacheFind cacheFind){
        Object result = null;
        try {
            //1.拼接redis存储数据的key
            Object[] args = joinPoint.getArgs();
            String key = cacheFind.preKey() +"::" + Arrays.toString(args);

            //2. 查询redis 之后判断是否有数据
            if(jedis.exists(key)){
                //redis中有记录,无需执行目标方法
                String json = jedis.get(key);
                //动态获取方法的返回值类型   向上造型  向下造型
                MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
                Class returnType = methodSignature.getReturnType();
                result = ObjectMapperUtil.toObj(json,returnType);
                System.out.println("AOP查询redis缓存");
            }else{
                //表示数据不存在,需要查询数据库
                result = joinPoint.proceed();  //执行目标方法及通知
                //将查询的结果保存到redis中去
                String json = ObjectMapperUtil.toJSON(result);
                //判断数据是否需要超时时间
                if(cacheFind.seconds()>0){
                    jedis.setex(key,cacheFind.seconds(),json);
                }else {
                    jedis.set(key, json);
                }
                System.out.println("aop执行目标方法查询数据库");
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

}
