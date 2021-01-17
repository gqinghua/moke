package com.moke.vas.main.service3;

import com.moke.vas.main.rabbitmq.ReliableEventBusQueue;
import com.moke.vas.core.utils.CircuitBreakerutil;
import com.moke.vas.core.verticle.MicroServiceVerticle;
import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.servicediscovery.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongdongqin on 2018-10-19.
 * without the style for solving monitor problems, by zipkin
 */
@Component
public class GeneratorConfigVerticle extends MicroServiceVerticle {

    private static final Logger LOGGER   = LoggerFactory.getLogger(GeneratorConfigVerticle.class);

    public static final String EVENT_ADDRESS = "market";

    @Autowired
    ReliableEventBusQueue eventBusQueue;

    @Override
    public void start() {
        super.start();
        /** here you can start up new verticle with dependencies**/
        VertxUtil.getVertxInstance().deployVerticle(MarketDataVerticle.class.getName(), new DeploymentOptions());
        VertxUtil.getVertxInstance().deployVerticle(RestQuoteAPIVerticle.class.getName(), new DeploymentOptions());

        // Publish the services in the discovery infrastructure, for discovery thread
        publishMessageSource("market-data", "market2", rec -> {
            if (!rec.succeeded()) {
                rec.cause().printStackTrace();
            }
            LOGGER.info("Market-Data service published : " + rec.succeeded());
        });



        /**breaker invoking, i think breaker should not be encapsulate, temporarily **/
        CircuitBreakerutil.init("for breaker testing", VertxUtil.getVertxInstance());
        CircuitBreakerutil.getVertxInstance().executeWithFallback(future -> {
            vertx.createHttpClient().getNow(8989, "localhost", "/restapp/listUsers", response -> {
                if (response.statusCode() != 200) {
                    LOGGER.error("HTTP sending error");
                    future.fail("HTTP error");
                } else {
                    response.bodyHandler(System.out::println);
                    future.complete();
                }
            });
        },v -> "123456");

        /** another situation is the breaker **/
        Future<String> result = CircuitBreakerutil.getVertxInstance().executeWithFallback(future -> {
          vertx.createHttpClient().getNow(8969, "localhost", "/restapp/listUsers", response -> {
                if (response.statusCode() != 200) {
                    LOGGER.error("HTTP error");
                    future.fail("HTTP error");
                } else {
                    response.bodyHandler(System.out::println);
                    future.complete();
                }
            });
        },v -> "circuit breaker invoking");

        result.setHandler(ar -> {
            // Do something with the result
            LOGGER.info("Result: " + ar.result());
        });

        /** multi thread dependency invoking. or vert.x compositeFuture **/
        CompletableFuture<Void> latch = new CompletableFuture<>();
        RabbitMQClient rabbitMQClient = eventBusQueue.getRabbitMQClient();
        rabbitMQClient.start(ar -> {
            if(ar.succeeded()) {
                latch.complete(null);
            } else {
                latch.completeExceptionally(ar.cause());
            }
        });

        /**block until mq connected successfully, and the future get the result  **/
        try{
            latch.get(10L, TimeUnit.SECONDS);

        }catch (Exception ex) {
            //TODO time out exception
        }

        /**simulate the rabbit mq sending periodly **/
        VertxUtil.getVertxInstance().setPeriodic(3000L, l -> {
            /** event bus sending with message **/
            eventBusQueue.basicPublish(eventBusQueue.getRabbitMQClient(), "", "myqueue", new JsonObject().put("body", "Hello RabbitMQ, from Vert.x !"));
        });

        eventBusQueue.bindMQEventBus(rabbitMQClient, "myaddress", "myqueue");

        /**service discovery by reference for message resource, do not use http and rpc temporarily **/
        /**set the value and invoke the value for ascyn method **/
        discovery.getRecord(new JsonObject().put("name", "market-data"), ar->  {
            if(ar.succeeded() && ar.result() != null) {
                //retrieve the service reference
               ServiceReference reference = discovery.getReference(ar.result());
               //retrieve the service object
                MessageConsumer<JsonObject> consumer = reference.getAs(MessageConsumer.class);
                //attach a message handler on it
                consumer.handler(message -> {
                    JsonObject payload = message.body();
                    System.out.println(message.body());
                });
            }
        });

    }
}
