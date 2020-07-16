package com.moke.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

// 声明消费消息的类，并在注解中指定，相关的消费信息
@Service
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
public class StringConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        System.out.print("------- StringConsumer received:"+ message);
    }

}