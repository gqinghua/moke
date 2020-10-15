package com.xxx.utils;

import com.xxx.model.ReplyObj;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;


/**
 * Created by XHD on 2018/3/15.
 */
public class HttpUtil {

    public static void fireJsonResponse(HttpServerResponse response, int statusCode, ReplyObj replyObj) {
        response.putHeader("content-type", "application/json; charset=utf-8").setStatusCode(statusCode).end(replyObj.toString());
    }

    public static void fireTextResponse(RoutingContext routingContext, String text) {
        routingContext.response().putHeader("content-type", "text/html; charset=utf-8").end(text);
    }

}
