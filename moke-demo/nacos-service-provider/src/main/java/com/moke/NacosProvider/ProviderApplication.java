package com.moke.NacosProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: sw
 * @ClassName ProviderApplication
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-13 16:12
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
