package org.stackroute;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerExampleApplication {

	private KafkaConsumer<String, String> consumer;

	public void initialize() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "testgroup");
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "300");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		consumer = new KafkaConsumer<String, String>(props);
	}

	public void consume() {

		consumer.subscribe(Arrays.asList("test4"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)
				System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConsumerExampleApplication.class, args);

		ConsumerExampleApplication myc = new ConsumerExampleApplication();
		// Configure Kafka consumer
		myc.initialize();
		// Start consumption
		myc.consume();
	}
}
