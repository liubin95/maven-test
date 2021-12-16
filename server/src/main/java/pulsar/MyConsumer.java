package pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

import lombok.extern.log4j.Log4j;

/**
 * Consumer.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-29 : base version.
 */
@Log4j
public class MyConsumer {

  public static void main(String[] args) throws PulsarClientException {
    try (PulsarClient client =
            PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();
        Consumer<String> consumer =
            client
                .newConsumer(Schema.STRING)
                .topic("my-topic")
                .subscriptionName("my-subscription")
                .subscribe()) {
      for (int i = 0; i < 50; i++) {
        final Message<String> receive = consumer.receive();
        log.info(receive.getValue());
        consumer.acknowledge(receive);
      }

      final Messages<String> messages = consumer.batchReceive();
      log.info(messages.size());
      log.info(messages.toString());
    }
  }
}
