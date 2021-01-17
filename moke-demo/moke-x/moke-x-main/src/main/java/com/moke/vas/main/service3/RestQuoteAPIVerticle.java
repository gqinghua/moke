package com.moke.vas.main.service3;

import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongdongqin on 2018-10-19.
 */
public class RestQuoteAPIVerticle extends AbstractVerticle {

    private static final Logger LOGGER   = LoggerFactory.getLogger(RestQuoteAPIVerticle.class);

    private Map<String, JsonObject> quotes = new HashMap<>();


    @Override
    public void start() {

        VertxUtil.getVertxInstance().eventBus().<JsonObject>consumer(GeneratorConfigVerticle.EVENT_ADDRESS)
                .handler(message -> {
                    LOGGER.info("receive event bus message from GeneratorConfigVerticle " + message.body().toString());
                    JsonObject quote = message.body();
                    quotes.put(quote.getString("name"), quote);
                });


        // Create the event bus handler which messages will be sent to
        VertxUtil.getVertxInstance().eventBus().consumer("myaddress", msg -> {
            JsonObject json = (JsonObject) msg.body();
            LOGGER.info("Got message from rabbitmq: " + json.toString());
            // ack
            //client.basicAck(json.getLong("deliveryTag"), false, asyncResult -> {
            });


        VertxUtil.getVertxInstance().createHttpServer()
                 .requestHandler(request -> {
                     HttpServerResponse response = request.response()
                             .putHeader("content-type", "application/json");
                     String name = request.getParam("name");
                     if(name == null) {
                         response.setStatusCode(404).end();
                     } else {
                         response.end(Json.encodePrettily(quotes));
                     }
                 }).listen(8089, ar-> {
                     if(ar.succeeded()) {
                         LOGGER.info("successfully listen");
                     } else {
                         LOGGER.info("failure listen");
                     }
        });
    }
}
