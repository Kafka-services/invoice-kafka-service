package com.invoice.invoiceconsumer.kafka.consumer;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

public class InvoiceConsumer implements Runnable {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;
	private final int id;
	private final AdminClient admin;

	public InvoiceConsumer(int id, String groupId, List<String> topics) {
		this.id = id;
		this.topics = topics;
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "group-id-json-1");
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		this.consumer = new KafkaConsumer<>(props);

		// Admin Client to check the existance of topic
		admin = AdminClient.create(props);
	}

	@Override
	public void run() {
		try {
			consumer.subscribe(topics);

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println("======= Invoice Consumer ======");
					System.out.println(record);
				}
			}
		} catch (WakeupException e) {
			// ignore for shutdown
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}

}