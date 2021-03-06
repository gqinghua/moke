package com.moke.recipe;

import com.moke.common.annotation.EnableLoginArgResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 作者
 */
@EnableLoginArgResolver
@EnableDiscoveryClient
@EnableFeignClients
//@EnableTransactionManagement
@SpringBootApplication
public class RecipeApp {
    public static void main(String[] args) {
        SpringApplication.run(RecipeApp.class, args);
    }


}
