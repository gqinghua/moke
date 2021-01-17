package com.moke.vas.core.vertx;

import io.vertx.core.Vertx;

import java.util.Objects;

/**
 * vertx singleton
 */
public final class VertxUtil {

    private static volatile Vertx singletonVertx;

    private VertxUtil() {

    }

    public static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "not initial vertx");
        if(singletonVertx == null) {
            synchronized (VertxUtil.class) {
                if(singletonVertx == null) {
                    singletonVertx = vertx;
                }
            }
        }
    }

    public static Vertx getVertxInstance() {
        Objects.requireNonNull(singletonVertx, "not initial vertx");
        return singletonVertx;
    }
}
