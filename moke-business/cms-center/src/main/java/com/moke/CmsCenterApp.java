package com.moke;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.moke.common.annotation.EnableLoginArgResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author
 */
@EnableLoginArgResolver
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication
@EnableMethodCache(basePackages = "com.moke.cms")
@EnableCreateCacheAnnotation
public class CmsCenterApp {
    public static void main(String[] args) {
        SpringApplication.run(CmsCenterApp.class, args);
    }
    /**
     * jetcache注解使用
     * @EnableMethodCache(basePackages = "com.company.mypackage")
     * @EnableCreateCacheAnnotation
     * EnableMethodCache，EnableCreateCacheAnnotation这两个注解分别激活Cached和CreateCache注解
     */
}
