//package com.moke.rocketmq;
//
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//
///**
// * @program: sw
// * @ClassName RocketMqTest
// * @description: [用一句话描述此类]
// * @author: GUO
// * @create: 2020-06-10 21:53
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RocketMqTest {
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Test
//    public void testRocketMq1() {
//
//        String name = "aaa";
//        rocketMQTemplate.convertAndSend("test-topic-1", name);
//        OrderPaidEvent orderPaidEvent = new OrderPaidEvent();
//        orderPaidEvent.setOrderId("5555");
//        rocketMQTemplate.send("test-topic-2", MessageBuilder.withPayload("55").build());
//
//        System.err.println("发送成功...");
//
//    }
//}
