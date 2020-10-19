package com.xxx.utils;

import com.xxx.vertx.VertxUtil;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

/**
 * @author
 * @date 2018/8/15
 */
public final class AsyncServiceUtil {

    public static <T> T getAsyncServiceInstance(Class<T> asClazz, Vertx vertx) {
        String address = asClazz.getName();
        return new ServiceProxyBuilder(vertx).setAddress(address).build(asClazz);
    }

    public static <T> T getAsyncServiceInstance(Class<T> asClazz) {
        String address = asClazz.getName();
        return new ServiceProxyBuilder(VertxUtil.getVertxInstance()).setAddress(address).build(asClazz);
    }
}