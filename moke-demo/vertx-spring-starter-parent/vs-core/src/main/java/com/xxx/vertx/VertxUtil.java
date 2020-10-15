package com.xxx.vertx;

import io.vertx.core.Vertx;

import java.util.Objects;

/**
 * vertx单例
 */
public final class VertxUtil {

    private static Vertx singletonVertx;

    private VertxUtil() {

    }

    public static void init(Vertx vertx) {
        Objects.requireNonNull(vertx, "未初始化Vertx");
        singletonVertx = vertx;
    }

    public static Vertx getVertxInstance() {
        Objects.requireNonNull(singletonVertx, "未初始化Vertx");
        return singletonVertx;
    }
}
