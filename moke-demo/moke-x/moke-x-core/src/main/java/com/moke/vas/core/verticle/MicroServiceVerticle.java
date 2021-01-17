package com.moke.vas.core.verticle;

import com.moke.vas.core.vertx.VertxUtil;
import io.vertx.core.*;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.servicediscovery.types.MessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * micro service with service publish
 */
public class MicroServiceVerticle extends AbstractVerticle {

    private static final Logger LOGGER   = LoggerFactory.getLogger(MicroServiceVerticle.class);

    protected ServiceDiscovery discovery;
    protected Set<Record> registeredRecords = new ConcurrentHashSet<>();

    @Override
    public void start() {
        LOGGER.info("MicroServiceVerticle +" + config());
        /**different discovery instance for each verticle, however, with the same vertx instance for this host **/
        discovery = ServiceDiscovery.create(VertxUtil.getVertxInstance(), new ServiceDiscoveryOptions().setBackendConfiguration(config()));
    }

    public void publishHttpEndpoint(String name, String host, Integer port, Handler<AsyncResult<Void>> completionHandler) {
        Record record = HttpEndpoint.createRecord(name, host, port, "/");
        publish(record, completionHandler);    }

    public void publishMessageSource(String name, String address, Class<?> contentClass, Handler<AsyncResult<Void>> completionHandler) {
        Record record = MessageSource.createRecord(name, address, contentClass);
        publish(record, completionHandler);
    }

    public void publishMessageSource(String name, String address, Handler<AsyncResult<Void>> completionHandler) {
        Record record = MessageSource.createRecord(name, address);
        publish(record, completionHandler);
    }

    public void publishEventBusService(String name, String address, Class<?> serviceClass, Handler<AsyncResult<Void>>
            completionHandler) {
        Record record = EventBusService.createRecord(name, address, serviceClass);
        publish(record, completionHandler);
    }

    protected void publish(Record record, Handler<AsyncResult<Void>> completionHandler) {
        if (discovery == null) {
            try {
                start();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create discovery service");
            }
        }
        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                registeredRecords.add(record);
            }
            completionHandler.handle(ar.map((Void)null));
        });

    }

    /**for each verticle, can unregister discovery service in the same vert.x instance **/
    @Override
    public void stop(Future<Void> future) throws Exception {
        List<Future> futures = new ArrayList<>();
        for (Record record : registeredRecords) {
            Future<Void> unregistrationFuture = Future.future();
            futures.add(unregistrationFuture);
            discovery.unpublish(record.getRegistration(), unregistrationFuture);
        }

        if (futures.isEmpty()) {
            discovery.close();
            future.complete();
        } else {
            CompositeFuture composite = CompositeFuture.all(futures);
            composite.setHandler(ar -> {
                discovery.close();
                if (ar.failed()) {
                    future.fail(ar.cause());
                } else {
                    future.complete();
                }
            });
        }
    }
}
