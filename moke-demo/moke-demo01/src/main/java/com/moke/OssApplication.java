package com.moke;

import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

/**
 * @program: sw
 * @ClassName OssApplication
 * @description: [用一句话描述此类]
 * @author: GUO
 * @create: 2020-06-13 11:00
 **/
@SpringBootApplication

public class OssApplication {
    public static final String BUCKET_NAME = "test-bucket";
    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(OssApplication.class, args);
    }
    @Bean
    public AppRunner appRunner() {
        return new AppRunner();
    }
    class AppRunner implements ApplicationRunner {

        @Autowired
        private OSS ossClient;
        @Override
        public void run(ApplicationArguments args) throws Exception {
            try {
                if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                    ossClient.createBucket(BUCKET_NAME);
                }
            } catch (Exception e) {
                System.err.println("oss handle bucket error: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced // 使用 @LoadBalanced注解修改构造的RestTemplate，使其拥有一个负载均衡功能
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
