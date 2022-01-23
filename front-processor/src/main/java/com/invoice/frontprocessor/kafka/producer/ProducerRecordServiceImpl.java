package com.invoice.frontprocessor.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;

public class ProducerRecordServiceImpl {

	private Properties getKafkaProperties() {
		// create instance for properties to access producer configs
		Properties props = new Properties();

		// Assign localhost id
		props.put("bootstrap.servers", "localhost:9092");

		// If the request fails, the producer can automatically retry,
		props.put("retries", 0);

		// Specify buffer size in config
		props.put("batch.size", 16384);

		// Reduce the no of requests less than 0
		props.put("linger.ms", 1);

		// The buffer.memory controls the total amount of memory available to the
		// producer for buffering.
		props.put("buffer.memory", 33554432);

		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// props.put("value.serializer",
		// "com.aviation.externalsystem.kafka.configuration.KafkaValueSerializer");
		props.put("value.serializer", "org.springframework.kafka.support.serializer.JsonSerializer");

		return props;
	}

	public void produceMessage(Object msgObject, String topicName) {
		Properties props = getKafkaProperties();

		Producer<String, Object> producer = new KafkaProducer<String, Object>(props);

		String keyName = topicName;
		
		ProducerRecord<String, Object> record1 = new ProducerRecord(topicName, 0, System.currentTimeMillis(), keyName,
				msgObject, new RecordHeaders().add(new RecordHeader(keyName, null)));
		try {
			producer.send(record1).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
