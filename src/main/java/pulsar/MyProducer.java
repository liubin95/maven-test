package pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

/**
 * Producer.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-29 : base version.
 */
public class MyProducer {
  public static void main(String[] args) throws PulsarClientException {
    try (PulsarClient client =
            PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build();
        Producer<String> stringProducer =
            client.newProducer(Schema.STRING).topic("my-topic").create()) {
      for (int i = 0; i < 100; i++) {
        stringProducer.send("My message " + i);
      }
    }
  }
}
