package kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * KafkaStreamTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-22 : base version.
 */
@Slf4j
public class KafkaStreamTest {
  public static final String INPUT_TOPIC = "streams-plaintext-input";
  public static final String OUTPUT_TOPIC = "streams-wordcount-output";

  public static void main(String[] args) {
    final Properties props = new Properties();
    props.putIfAbsent(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
    props.putIfAbsent(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "LAPTOP-MOJU49C3.localdomain:9092");
    props.putIfAbsent(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
    props.putIfAbsent(
        StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.putIfAbsent(
        StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.putIfAbsent(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    final StreamsBuilder builder = new StreamsBuilder();
    final KStream<String, String> source = builder.stream(INPUT_TOPIC);

    final KStream<String, String> wordStream =
        source.flatMapValues(value -> Arrays.asList(value.split("\\W+")));

    val groupByStream = wordStream.groupBy((key, value) -> value);

    val count = groupByStream.count(Materialized.as("counts-store"));

    // need to override value serde to Long type
    count.toStream().to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    final Topology topology = builder.build();
    final KafkaStreams streams = new KafkaStreams(topology, props);
    final CountDownLatch latch = new CountDownLatch(1);

    log.info(topology.describe().toString());
    // attach shutdown handler to catch control-c
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread("streams-shutdown-hook") {
              @Override
              public void run() {
                streams.close();
                latch.countDown();
              }
            });
    try {
      streams.start();
      latch.await();
    } catch (Throwable e) {
      System.exit(1);
    }
    System.exit(0);
  }
}
