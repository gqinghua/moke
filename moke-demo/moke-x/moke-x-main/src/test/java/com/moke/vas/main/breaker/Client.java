package com.moke.vas.main.breaker;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;

/**
 * @author <a href="pahan.224@gmail.com">Pahan</a>
 */

public class Client extends AbstractVerticle {

  public static void main(String[] args) {
    Launcher.executeCommand("run", Client.class.getName());
  }

  @Override
  public void start() {
    CircuitBreakerOptions options = new CircuitBreakerOptions()
        .setMaxFailures(23)
        .setTimeout(10000)
        .setFallbackOnFailure(true);

    CircuitBreaker breaker =
        CircuitBreaker.create("my-circuit-breaker", vertx, options);


    for (int i=0; i < 20; i ++) {



    Future<String> result = breaker.executeWithFallback(future -> {
      vertx.createHttpClient().getNow(8989, "localhost", "/restapp/listUsers", response -> {
        if (response.statusCode() != 200) {
          System.out.println("HTTP sending error");
          future.fail("HTTP error");
          System.out.println("the status is " + breaker.state());

        } else {
         // System.out.println("it is ok now");

          response.bodyHandler(buffer -> {
            String responseMessage = buffer.toJsonObject().toString();
            System.out.println(responseMessage);
            future.complete(responseMessage);

          });
          System.out.println("the status is " + breaker.state());

        }
      });
    },v -> "fallback");

    result.setHandler(ar -> {
      // Do something with the result
      System.out.println("Result: " + ar.result());
      System.out.println("the status is " + breaker.state());


    });

    }

    System.out.println("success or not");
  }
}
