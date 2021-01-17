package com.moke.vas.core.vertx;

import io.vertx.ext.web.Router;

/**
 * router single instance
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
