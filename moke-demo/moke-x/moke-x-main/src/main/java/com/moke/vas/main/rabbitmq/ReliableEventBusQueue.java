package com.moke.vas.main.rabbitmq;

import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dongdongqin on 2018-10-23.
 * how to build the reliable mq to store event bus message?
 */

@Component
public class ReliableEventBusQueue {

    private final static Logger logger = LoggerFactory.getLogger(ReliableEventBusQueue.class);


    @Autowired
    private RabbitMQOptions rabbitMQOptions;

    private RabbitMQClient client;

    public ReliableEventBusQueue(){

    }

    /**
     * get the RabbitMQClient
     * @return
     */
    public RabbitMQClient getRabbitMQClient() {
        if(VertxUtil.getVertxInstance() != null) {
            if(client == null){
                synchronized (ReliableEventBusQueue.class){
                    if(client == null){
                        client = RabbitMQClient.create(VertxUtil.getVertxInstance(), rabbitMQOptions);
                    }
                }
            }
        }
        return client;
    }


    /**
     * exchangeDeclareWithConfig
     *
     * @param client
     * @param exchangeName
     * @param exchangeType
     */
    public void exchangeDeclareWithConfig(RabbitMQClient client, String exchangeName, String exchangeType) {
        JsonObject config = new JsonObject();
        config.put("x-dead-letter-exchange", "my.deadletter.exchange");
        config.put("alternate-exchange", "my.alternate.exchange");
        client.exchangeDeclare(exchangeName, exchangeType, true, false, config, onResult -> {
            if (onResult.succeeded()) {
                logger.info("Exchange successfully declared with config");
            } else {
                onResult.cause().printStackTrace();
            }
        });
    }

    /**
     * queueDeclareWithConfig
     * @param client
     */
    public void queueDeclareWithConfig(RabbitMQClient client, String queueName, JsonObject jsonObject) {
        //JsonObject config = new JsonObject();
        //config.put("x-message-ttl", 10_000L);
        client.queueDeclare(queueName, true, false, false, jsonObject, queueResult -> {
            if (queueResult.succeeded()) {
                logger.info("Queue declared!");
            } else {
                logger.info("Queue failed to be declared!");
                System.out.println(queueResult.cause());
            }
        });
    }


    /**
     * basicPublish
     * @param client
     * @param exchangeName
     * @param routingKey
     * @param jsonObject
     */
    public void basicPublish(RabbitMQClient client, String exchangeName, String routingKey, JsonObject jsonObject) {
        //JsonObject message = new JsonObject().put("body", "Hello RabbitMQ, from Vert.x !");
            client.basicPublish(exchangeName, routingKey, jsonObject, pubResult -> {
            if (pubResult.succeeded()) {
                logger.info("Message published !");
            } else {
                pubResult.cause().printStackTrace();
            }
        });
    }

    /**
     * Publish a message to a queue and confirm the broker acknowledged it.
     * @param client
     * @param jsonObject
     */
    public void basicPublishWithConfirm(RabbitMQClient client, String exchangeName, String routingKey, JsonObject jsonObject) {
        //JsonObject message = new JsonObject().put("body", "Hello RabbitMQ, from Vert.x !");
        // Put the channel in confirm mode. This can be done once at init.
        client.confirmSelect(confirmResult -> {
            if(confirmResult.succeeded()) {
                client.basicPublish(exchangeName, routingKey, jsonObject, pubResult -> {
                    if (pubResult.succeeded()) {
                        // Check the message got confirmed by the broker.
                        client.waitForConfirms(waitResult -> {
                            if(waitResult.succeeded()){
                                logger.info("Message published !");
                            }
                            else{
                               logger.error(waitResult.cause().toString());
                            }
                        });
                    } else {
                        logger.error(pubResult.cause().toString());
                    }
                });
            } else {
                logger.error(confirmResult.cause().toString());
            }
        });
    }


    /**
     * binding MQ and EventBus address
     * @param client
     * @param consumerAddress
     * @param queueName
     */
    public void bindMQEventBus(RabbitMQClient client, String consumerAddress, String queueName) {
        // Setup the link between rabbitmq consumer and event bus address
        client.basicConsume(queueName, consumerAddress, true, consumeResult -> {
            if (consumeResult.succeeded()) {
                logger.info("RabbitMQ consumer created !");
            } else {
                System.out.println("rabbitmq consumer fail");
                logger.info(consumeResult.cause().toString());
            }
        });
    }
}
