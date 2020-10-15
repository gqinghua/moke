package com.xxx.web;

import com.xxx.anno.RouteHandler;
import com.xxx.anno.RouteMapping;
import com.xxx.anno.RouteMethod;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * @author Xu Haidong
 * @date 2018/8/16
 */
@RouteHandler(order = 1)
public class MyFilter {

    @RouteMapping(value = "*", method = RouteMethod.ROUTE)
    public Handler<RoutingContext> myFilter() {
        return ctx -> {
            System.err.println("我是myFilter过滤器！");
            ctx.next();
        };
    }
}
