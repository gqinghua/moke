package com.moke.vas.main;

import com.moke.vas.main.service3.GeneratorConfigVerticle;
import com.hazelcast.config.Config;
import com.moke.vas.core.handlerfactory.RouterHandlerFactory;
import com.moke.vas.core.vertx.DeployVertxServer;
import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * Runner for the vertx-spring sample
 *
 */
@SpringBootApplication
@ComponentScan("com.moke.vas")
public class App {

    private static Logger LOGGER = LoggerFactory.getLogger(App.class);

    /**
     * web api package path
     */
    @Value("${web-api-packages}")
    private String webApiPackages;

    /**
     * async package path
     */
    @Value("${async-service-impl-packages}")
    private String asyncServiceImplPackages;

    /**
     * http server port
     */
    @Value("${http-server-port}")
    private int httpServerPort;

    /**
     * thread pool number
     */
    @Value("${worker-pool-size}")
    private int workerPoolSize;

    /**
     * async service instance number, suggest that it is same with cpu core
     */
    @Value("${async-service-instances}")
    private int asyncServiceInstances;

    /**generator vertical **/
    @Autowired
    GeneratorConfigVerticle generatorConfigVerticle;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * context publish events and then that application
     * is ready to receive request
     * The source of the event is the SpringApplication itself
     * invoking after spring initialized
     */
    @EventListener
    public void deployVerticles(ContextRefreshedEvent event) {
        Config hazelcastConfig = new Config();
        hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1").setEnabled(true);
        hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        ClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);

        EventBusOptions eventBusOptions = new EventBusOptions();
        eventBusOptions.setClustered(true);
        //for just debug, you can try another time out setting
        eventBusOptions.setConnectTimeout(1000 * 60 * 30);

        VertxOptions options = new VertxOptions()
                .setClusterManager(mgr)
                .setWorkerPoolSize(workerPoolSize)
                .setEventBusOptions(eventBusOptions)
                .setBlockedThreadCheckInterval(999999999L)
                .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
                .setClustered(true);


        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                System.out.println("cluster success start up");
                Vertx vertx = res.result();
                /** vertx instance**/
                VertxUtil.init(vertx);
                try {
                    Router router = new RouterHandlerFactory(webApiPackages).createRouter();
                    DeployVertxServer.startDeploy(router, asyncServiceImplPackages, httpServerPort, asyncServiceInstances);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                vertx.deployVerticle(generatorConfigVerticle);

           //     vertx.deployVerticle(new SpringDemoVerticle());
           //     vertx.deployVerticle(new ServerVerticle(8043));

           // } else {

            }
        });

        /**
         * JsonObject zkConfig = new JsonObject();
         zkConfig.put("zookeeperHosts", "127.0.0.1");
         zkConfig.put("rootPath", "io.vertx");
         zkConfig.put("retry", new JsonObject()
         .put("initialSleepTime", 3000)
         .put("maxTimes", 3));
         ZookeeperClusterManager mgr = new ZookeeperClusterManager();
         VertxOptions options = new VertxOptions().setClusterManager(mgr);


        RabbitMQClient rabbitMQClient = reliableEventBusQueue.getRabbitMQClient();
        JsonObject config = new JsonObject();
        config.put("x-message-ttl", 10_000L);
        reliableEventBusQueue.queueDeclareWithConfig(rabbitMQClient, "myqueue", config);
        reliableEventBusQueue.consumeWithManualAck(VertxUtil.getVertxInstance(), rabbitMQClient, "myaddress", "myqueue");
        JsonObject message = new JsonObject().put("body", "Hello RabbitMQ, from Vert.x !");
        reliableEventBusQueue.basicPublish(rabbitMQClient, "", "myqueue", message);
         *
         *
         *
         *
         */

    }
}

