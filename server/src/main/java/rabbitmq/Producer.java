package rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Main.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-07-16 : base version.
 */
public class Producer {

  public static void main(String[] args) throws IOException, TimeoutException {
    final ConnectionFactory factory = new ConnectionFactory();
    // "guest"/"guest" by default, limited to localhost connections
    factory.setUsername("guest");
    factory.setPassword("guest");
    factory.setHost("localhost");
    factory.setPort(5672);
    try (Connection conn = factory.newConnection();
        Channel channel = conn.createChannel()) {
      final String exchangeName = "my-exchange";
      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
      final String queueName = "my-queue";
      channel.queueBind(queueName, exchangeName, "routing-key");
      byte[] messageBodyBytes = "Hello, world!-12".getBytes();
      channel.basicPublish(exchangeName, "routing-key", null, messageBodyBytes);
    }
  }
}
