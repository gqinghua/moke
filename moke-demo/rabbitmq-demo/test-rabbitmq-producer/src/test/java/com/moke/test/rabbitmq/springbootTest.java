package com.moke.test.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.moke.RibbitMQTest.config.RabbitMQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName springbootTest
 * @Description TODO
 * @Author 郭清华
 * @Date 2019/8/5 18:46
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class springbootTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void rabbitmqTest(){
        for (int i = 0; i < 5; i++) {
           String msessg = "sms email inform to user"+i;
           rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPICS_INFORM,"inform.sms.email",msessg);
            System.out.println("send message is "+msessg+"");
        }
    }

    @Test
    public void rabbitmqTest2(){
        for (int i = 0; i < 5; i++) {
            String msessg = "sms email inform to user"+i;
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_INFORM_SMS,"inform.sms.ssm",msessg);
            System.out.println("send message is "+msessg+"");
        }
    }
    @Test
    public void testSendPostPage(){

        Map message = new HashMap<>();
        message.put("pageId","5a795ac7dd573c04508f3a56");
        //将消息对象转成json串
        String messageString = JSON.toJSONString(message);
        //路由key，就是站点ID
        String routingKey = "5a751fab6abb5044e0d19ea1";
        /**
         * 参数：
         * 1、交换机名称
         * 2、routingKey
         * 3、消息内容
         */
        rabbitTemplate.convertAndSend("ex_routing_cms_postpage",routingKey,messageString);

    }

}
