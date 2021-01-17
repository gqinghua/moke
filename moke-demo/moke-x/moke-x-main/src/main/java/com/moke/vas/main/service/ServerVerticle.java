package com.moke.vas.main.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

import java.util.Set;

/**
 * Simple web server verticle to expose the results of the Spring service bean call (routed via a verticle - see
 * SpringDemoVerticle)
 */
public class ServerVerticle extends AbstractVerticle {

    int port;
    private ServiceDiscovery discovery;
    protected Set<Record> registeredRecords = new ConcurrentHashSet<>();


    public ServerVerticle(int port){
        super();
        this.port = port;
    }
    @Override
    public void start() throws Exception {
        super.start();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(req -> {
            if (req.method() == HttpMethod.GET) {
                req.response().setChunked(true);

                if (req.path().equals("/products")) {

                    vertx.eventBus().<String>publish(SpringDemoVerticle.ALL_PRODUCTS_ADDRESS, "how are you111");

                    // listener for consuming
                    HttpEndpoint.getClient(discovery, new JsonObject().put("name", SpringDemoVerticle.ALL_PRODUCTS_ADDRESS), client -> {
                        if(client.failed()) {
                            // it failed
                            System.out.println(client.cause());
                        } else  {
                            HttpClient httpClient = client.result();
                            System.out.println("successfully listen one http servie");
                        }
                    });


                    req.response().setStatusCode(200).write("how are you111345").end();

                } else {
                   // System.out.println("Printing");
                    req.response().setStatusCode(200).write("Hello from vert.x").end();
                }

            } else {
                // We only support GET for now
                req.response().setStatusCode(405).end();
            }
        });

        server.listen(port);

        // publish http service
        publishHttpEndpoint(SpringDemoVerticle.ALL_PRODUCTS_ADDRESS, "localhost", port, ar -> {
            if (ar.failed()) {
                ar.cause().printStackTrace();
            } else {
                System.out.println("Quotes (Rest endpoint) service published : " + ar.succeeded());
            }
        });
    }

    /**
     * publishHttpEndpoint
     * @param name
     * @param host
     * @param port
     * @param completionHandler
     */
    public void publishHttpEndpoint(String name, String host, int port, Handler<AsyncResult<Void>> completionHandler) {

        Record record = HttpEndpoint.createRecord(name, host, port, "/");
        publish(record, completionHandler);
    }

    /**
     * publish service
     * @param record
     * @param completionHandler
     */
    public void publish(Record record, Handler<AsyncResult<Void>> completionHandler){

        if(discovery == null) {
            discovery = ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions());
        }
        discovery.publish(record, ar ->{
            if(ar.succeeded()) {
                registeredRecords.add(record);
                completionHandler.handle(ar.map((Void)null));
            }
        });

    }

}
