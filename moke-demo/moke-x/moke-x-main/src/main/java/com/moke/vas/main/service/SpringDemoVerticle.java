package com.moke.vas.main.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;


/**
 * Simple verticle to wrap a Spring service bean - note we wrap the service call
 * in executeBlocking, because we know it's going to be a JDBC call which blocks.
 * As a general principle with Spring beans, the default assumption should be that it will block unless you
 * know for sure otherwise (in other words use executeBlocking unless you know for sure your service call will be
 * extremely quick to respond)
 */
public class SpringDemoVerticle extends AbstractVerticle {

    public static final String ALL_PRODUCTS_ADDRESS = "example.all.products";

    private final ObjectMapper mapper = new ObjectMapper();

    public SpringDemoVerticle() {

    }

    /*private Handler<Message<String>> allProductsHandler(ProductService service)  {

       // throw new Exception("");

        *//*return msg -> vertx.<String>executeBlocking(future -> {
                    throw new Exception("");
                    try {
                        future.complete(mapper.writeValueAsString(service.getAllProducts()));
                    } catch (JsonProcessingException e) {
                        System.out.println("Failed to serialize result");
                        future.fail(e);
                    }
                },
                result -> {
                    if (result.succeeded()) {
                        msg.reply(result.result());
                    } else {
                        msg.reply(result.cause().toString());
                    }
                });*//*
    }
*/
    @Override
    public void start() throws Exception {
        super.start();


      vertx.eventBus().consumer(ALL_PRODUCTS_ADDRESS, message -> {
            System.out.println("1 received message.body() = "
                    + message.body());
        });

         /*
        //Start consuming events
        vertx.eventBus().<String>consumer(ALL_PRODUCTS_ADDRESS).handler(msg -> {
            System.out.println(msg);
        });
        */
    }

}
