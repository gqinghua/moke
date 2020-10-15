package com.xxx.vertx;

import com.xxx.verticle.AsyncRegistVerticle;
import com.xxx.verticle.RouterRegistryVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 开始注册vertx相关服务
 */
public class DeployVertxServer {

    private static Logger LOGGER = LoggerFactory.getLogger(DeployVertxServer.class);

    public static void startDeploy(Router router, String asyncServiceImplPackages, int port, int asyncServiceInstances) throws IOException {
        LOGGER.debug("Start Deploy....");
        LOGGER.debug("Start registry router....");
        VertxUtil.getVertxInstance().deployVerticle(new RouterRegistryVerticle(router, port));
        LOGGER.debug("Start registry service....");
        if (asyncServiceInstances < 1) {
            asyncServiceInstances = 1;
        }
        for (int i = 0; i < asyncServiceInstances; i++) {
            VertxUtil.getVertxInstance().deployVerticle(new AsyncRegistVerticle(asyncServiceImplPackages), new DeploymentOptions().setWorker(true));
        }
    }

    public static void startDeploy(Router router, String asyncServiceImplPackages, int asyncServiceInstances) throws IOException {
        LOGGER.debug("Start Deploy....");
        LOGGER.debug("Start registry router....");
        VertxUtil.getVertxInstance().deployVerticle(new RouterRegistryVerticle(router));
        LOGGER.debug("Start registry service....");
        if (asyncServiceInstances < 1) {
            asyncServiceInstances = 1;
        }
        for (int i = 0; i < asyncServiceInstances; i++) {
            VertxUtil.getVertxInstance().deployVerticle(new AsyncRegistVerticle(asyncServiceImplPackages), new DeploymentOptions().setWorker(true));
        }
    }
}
