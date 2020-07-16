package com.rocketmq.demo.config;


import com.rocketmq.demo.config.RocketMqConfig.MySource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;


/**
 * 配置消息生产者
 *
 * @author
 */
@EnableBinding({MySource.class})
public class RocketMqConfig {
    public interface MySource {
        @Output(Source.OUTPUT)
        MessageChannel output();
    }
}
