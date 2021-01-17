package com.moke.vas.main.service3;

import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by dongdongqin on 2018-10-19.
 */
@Component
public class MarketDataVerticle extends AbstractVerticle {

    private static final Logger LOGGER   = LoggerFactory.getLogger(MarketDataVerticle.class);

    @Override
    public void start() {
        VertxUtil.getVertxInstance().setPeriodic(3000L, l -> {
            /** event bus sending with message **/
            VertxUtil.getVertxInstance().eventBus().publish(GeneratorConfigVerticle.EVENT_ADDRESS, toJson());
            VertxUtil.getVertxInstance().eventBus().publish("market2", toJson2());

        });

    }

    /**simulate the json data  **/
    private JsonObject toJson() {
        return new JsonObject()
                .put("exchange", "vert.x stock exchange")
                .put("symbol", "symbol")
                .put("name", "qindongdong")
                .put("bid", "2000")
                .put("ask", "1000")
                .put("volume", "100")
                .put("open", "open")
                .put("shares", "shares");

    }

    /**simulate the json data  **/
    private JsonObject toJson2() {
        return new JsonObject()
                .put("exchange", "vert.x stock exchange")
                .put("symbol", "symbol");

    }

}
