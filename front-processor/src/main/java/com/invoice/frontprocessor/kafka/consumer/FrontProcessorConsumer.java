package com.invoice.frontprocessor.kafka.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.invoice.frontprocessor.kafka.producer.ProducerRecordServiceImpl;
import com.invoice.frontprocessor.model.TopicMap;
import com.invoice.frontprocessor.model.TopicMetaData;

public class FrontProcessorConsumer implements Runnable {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;
	private final int id;
	private final TopicMetaData topicMetaData;
	private final AdminClient admin;

	public FrontProcessorConsumer(int id, String groupId, List<String> topics, TopicMetaData topicMetaData) {
		this.id = id;
		this.topics = topics;
		this.topicMetaData = topicMetaData;
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
					Map<String, Object> data = new HashMap<>();
					data.put("partition1", record.partition());
					data.put("offset", record.offset());
					data.put("value", record.value());
					processMessage(record);
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

	private void processMessage(ConsumerRecord record) {
		HashMap<String, Object> incomingMap = parseMessageAsMap(record);

		if (topicMetaData != null) {
			for (TopicMap topicMap : topicMetaData.getTopicMap()) {
				ProducerRecordServiceImpl serviceImpl = new ProducerRecordServiceImpl();

				Map<String, Object> outputMap = new HashMap<>();
				if (topicMap != null) {
					for (String field : topicMap.getFields()) {
						if (incomingMap.containsKey(field)) {
							outputMap.put(field, incomingMap.get(field));
						}
					}
				}
				try {
					String json = new ObjectMapper().writeValueAsString(outputMap);
					serviceImpl.produceMessage(json, topicMap.getTopicName());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private HashMap<String, Object> parseMessageAsMap(ConsumerRecord record) {
		if (record != null && record.value() != null) {
			HashMap<String, Object> map = new Gson().fromJson(record.value().toString(), HashMap.class);
			return map;
		}
		return null;
	}
}