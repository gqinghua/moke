package com.moke.vas.core.utils;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Vertx;

import java.util.Objects;

/**
 * Created by dongdongqin on 2018-10-22.
 */
public class CircuitBreakerutil {

    private static volatile CircuitBreaker singleCircuitBreaker;

    private CircuitBreakerutil() {

    }

    /**
     * TODO fix me if confirm
     **/
    public static void init(String name, Vertx vertx) {
        Objects.requireNonNull(vertx, "not initialize circuitBreaker");
        if (singleCircuitBreaker == null) {
            synchronized (CircuitBreakerutil.class) {
                if (singleCircuitBreaker == null) {
                    singleCircuitBreaker = CircuitBreaker.create(name, vertx,
                            new CircuitBreakerOptions()
                                    .setMaxFailures(2)
                                    .setFallbackOnFailure(true)
                                     /** this will retry after 2000, set status as close**/
                                    .setResetTimeout(2000)
                                     /**this invoke timeout setting **/
                                    .setTimeout(5000));
                }
            }
        }

    }

    public static CircuitBreaker getVertxInstance() {
        return singleCircuitBreaker;
    }

}
