package com.xxx;

import com.xxx.handlerfactory.RouterHandlerFactory;
import com.xxx.vertx.DeployVertxServer;
import com.xxx.vertx.VertxUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * @author Xu Haidong
 * @date 2018/8/2
 */
@SpringBootApplication
//@ComponentScan("com.xxx")
public class App {
    /**
     * web api所在包路径
     */
    @Value("${web-api-packages}")
    private String webApiPackages;

    /**
     * 异步服务所在包路径
     */
    @Value("${async-service-impl-packages}")
    private String asyncServiceImplPackages;

    /**
     * http服务器端口号
     */
    @Value("${http-server-port}")
    private int httpServerPort;

    /**
     * 工作线程池大小（可根据实际情况调整）
     */
    @Value("${worker-pool-size}")
    private int workerPoolSize;

    /**
     * 异步服务实例数量（建议和CPU核数相同）
     */
    @Value("${async-service-instances}")
    private int asyncServiceInstances;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @EventListener
    public void deployVerticles(ApplicationReadyEvent event) {
        EventBusOptions eventBusOptions = new EventBusOptions();
        //便于调试 设定超时等时间较长 生产环境建议适当调整
        eventBusOptions.setConnectTimeout(1000 * 60 * 30);
        Vertx vertx = Vertx.vertx(
                new VertxOptions().setWorkerPoolSize(workerPoolSize)
                        .setEventBusOptions(eventBusOptions)
                        .setBlockedThreadCheckInterval(999999999L)
                        .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
        );
        VertxUtil.init(vertx);
        try {
            Router router = new RouterHandlerFactory(webApiPackages).createRouter();
            DeployVertxServer.startDeploy(router, asyncServiceImplPackages, httpServerPort, asyncServiceInstances);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
