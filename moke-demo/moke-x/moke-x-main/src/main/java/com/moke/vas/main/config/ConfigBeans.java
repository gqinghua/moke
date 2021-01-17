package com.moke.vas.main.config;

import com.hazelcast.config.Config;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.rabbitmq.RabbitMQOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dongdongqin on 2018-10-18.
 */

@Configuration
public class ConfigBeans {

    private final static Logger logger = LoggerFactory.getLogger(ConfigBeans.class);

    //@Value("${worker-pool-size}")
    private Integer workerPoolSize;

    /**
     * set event bus options
     * EventBusOptions can use serverId and port, host (do not do that)
     * EventBusOptions can set ssl and clientAuth
     * @return
     */
    //@Bean
    public EventBusOptions getEventBusOptions(){
        EventBusOptions eventBusOptions = new EventBusOptions();
        eventBusOptions.setClustered(true);
        eventBusOptions.setConnectTimeout(1000 * 60 * 30);
        return eventBusOptions;
    }

    /**
     * configure cluster and join the network with cluster way
     * @return VertxOptions
     */
    //@Bean
    public VertxOptions getVertxOptions() {
        Config hazelcastConfig = new Config();
        hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().addMember("127.0.0.1").setEnabled(true);
        hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(true);
        ClusterManager mgr  = new HazelcastClusterManager(hazelcastConfig);

        return new VertxOptions()
                .setWorkerPoolSize(workerPoolSize)
                .setEventBusOptions(getEventBusOptions())
                .setBlockedThreadCheckInterval(Long.MAX_VALUE)
                .setMaxEventLoopExecuteTime(Long.MAX_VALUE)
                .setClustered(true)
                .setClusterManager(mgr);
    }

    @Bean
    public RabbitMQOptions rabbitMQOptions() {
        return new RabbitMQOptions().setUser("guest")
                .setPassword("guest")
                .setHost("localhost")
                .setPort(5672)
                .setConnectionTimeout(6000)
                .setRequestedChannelMax(5)
                .setNetworkRecoveryInterval(500)
                .setAutomaticRecoveryEnabled(true);
    }

}
