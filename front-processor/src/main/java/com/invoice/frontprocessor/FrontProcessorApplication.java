package com.invoice.frontprocessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoice.frontprocessor.constant.ApplicationConstant;
import com.invoice.frontprocessor.kafka.consumer.FrontProcessorConsumer;
import com.invoice.frontprocessor.model.TopicMetaData;

@SpringBootApplication
public class FrontProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontProcessorApplication.class, args);
		TopicMetaData topicMetaData = readJsonMetaData();
		runThreadToConsumeRecords(topicMetaData);
	}

	private static TopicMetaData readJsonMetaData() {
		// create Object Mapper
		ObjectMapper mapper = new ObjectMapper();

		// read JSON file and map/convert to java POJO
		try {
			File file = ResourceUtils.getFile("classpath:topicMetaData.json");
			TopicMetaData topicMetaData = mapper.readValue(file, TopicMetaData.class);
			return topicMetaData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void runThreadToConsumeRecords(TopicMetaData topicMetaData) {
		int numConsumers = 3;
		String groupId = ApplicationConstant.GROUP_ID_JSON;
		List<String> topics = Arrays.asList(ApplicationConstant.TOPIC_NAME);
		ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

		final List<FrontProcessorConsumer> consumers = new ArrayList<>();
		for (int i = 0; i < numConsumers; i++) {
			FrontProcessorConsumer consumer = new FrontProcessorConsumer(i, groupId, topics, topicMetaData);
			consumers.add(consumer);
			executor.submit(consumer);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (FrontProcessorConsumer consumer : consumers) {
					consumer.shutdown();
				}
				executor.shutdown();
				try {
					executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
