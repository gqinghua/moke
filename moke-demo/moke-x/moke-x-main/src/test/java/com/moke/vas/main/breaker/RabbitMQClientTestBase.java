package com.moke.vas.main.breaker;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;
import io.vertx.test.core.VertxTestBase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.vertx.test.core.TestUtils.randomAlphaString;

public class RabbitMQClientTestBase extends VertxTestBase {

  public static final String CLOUD_AMQP_URI = "amqps://xvjvsrrc:VbuL1atClKt7zVNQha0bnnScbNvGiqgb@moose.rmq.cloudamqp" +
    ".com/xvjvsrrc";

  protected RabbitMQClient client;
  protected Channel channel;

  protected void connect() throws Exception {
    if (client != null) {
      throw new IllegalStateException("Client already started");
    }
    RabbitMQOptions config = config();
    System.out.println(config.getPort());
    client = RabbitMQClient.create(vertx, config);
    CompletableFuture<Void> latch = new CompletableFuture<>();
    client.start(ar -> {
      if (ar.succeeded()) {
        latch.complete(null);
      } else {
        latch.completeExceptionally(ar.cause());
      }
    });
    latch.get(10L, TimeUnit.SECONDS);
    ConnectionFactory factory = new ConnectionFactory();
    if (config.getUri() != null) {
      factory.setUri(config.getUri());
    }
    channel = factory.newConnection().createChannel();
  }

  public RabbitMQOptions config() throws Exception {
    RabbitMQOptions config = new RabbitMQOptions();
    config.setUser("guest")
            .setPassword("guest")
            .setHost("127.0.0.1")
            .setPort(15672)
            .setConnectionTimeout(6000)
            .setRequestedChannelMax(5)
            .setNetworkRecoveryInterval(500)
            .setAutomaticRecoveryEnabled(true);
    return config;
  }

  @Override
  protected void tearDown() throws Exception {
    if (channel != null) {
      channel.close();
    }
    super.tearDown();
  }

  String setupQueue(Set<String> messages) throws Exception {
    return setupQueue(messages, null);
  }

  String setupQueue(Set<String> messages, String contentType) throws Exception {
    String queue = randomAlphaString(10);
    AMQP.Queue.DeclareOk ok = channel.queueDeclare(queue, false, false, true, null);
    assertNotNull(ok.getQueue());
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
      .contentType(contentType).contentEncoding("UTF-8").build();

    if (messages != null) {
      for (String msg : messages) {
        channel.basicPublish("", queue, properties, msg.getBytes("UTF-8"));
      }
    }
    return queue;
  }

  Set<String> createMessages(int number) {
    Set<String> messages = new HashSet<>();
    for (int i = 0; i < number; i++) {
      messages.add(randomAlphaString(20));
    }
    return messages;
  }
}
