package rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * rabbitmq.Consumer.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-07-16 : base version.
 */
public class Consumer {

  public static void main(String[] args)
      throws IOException, TimeoutException, InterruptedException {
    final ConnectionFactory factory = new ConnectionFactory();
    // "guest"/"guest" by default, limited to localhost connections
    factory.setUsername("guest");
    factory.setPassword("guest");
    factory.setHost("localhost");
    factory.setPort(5672);
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {
      final String queueName = "my-queue";
      channel.basicConsume(
          queueName,
          true,
          new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                String consumerTag,
                Envelope envelope,
                AMQP.BasicProperties properties,
                byte[] body) {
              System.out.println(new String(body));
            }
          });
      TimeUnit.MINUTES.sleep(10L);
    }
  }
}
