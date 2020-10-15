package com.xxx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由发布
 */
public class RouterRegistryVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouterRegistryVerticle.class);

    private static final int MAX_WEBSOCKET_FRAME_SIZE = 1000000;

    private static final int HTTP_PORT = 8080;

    private int port = HTTP_PORT;

    private HttpServer server;

    private Router router;

    public RouterRegistryVerticle(Router router) {
        this.router = router;
    }

    public RouterRegistryVerticle(Router router, int port) {
        this.router = router;
        if (port > 0) {
            this.port = port;
        }
    }

    @Override
    public void start(Future<Void> future) throws Exception {
        LOGGER.debug("To start listening to port {} ......", port);
        super.start();
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(MAX_WEBSOCKET_FRAME_SIZE).setPort(port);
        server = vertx.createHttpServer(options);
        server.requestHandler(router::accept);
        server.listen(result -> {
            if (result.succeeded()) {
                future.complete();
            } else {
                future.fail(result.cause());
            }
        });
    }

    @Override
    public void stop(Future<Void> future) {
        if (server == null) {
            future.complete();
            return;
        }
        server.close(result -> {
            if (result.failed()) {
                future.fail(result.cause());
            } else {
                future.complete();
            }
        });
    }
}
