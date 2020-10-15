package com.xxx.vertx;

import io.vertx.ext.web.Router;

/**
 * router单例
 */
public final class RouterUtil {

    private static Router router;

    private RouterUtil() {
    }

    public static Router getRouter() {
        if (router == null) {
            router = Router.router(VertxUtil.getVertxInstance());
        }
        return router;
    }
}
