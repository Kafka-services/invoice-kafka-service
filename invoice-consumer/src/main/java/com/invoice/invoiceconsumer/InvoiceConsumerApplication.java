package com.invoice.invoiceconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.invoice.invoiceconsumer.constant.ApplicationConstant;
import com.invoice.invoiceconsumer.kafka.consumer.InvoiceConsumer;

@SpringBootApplication
public class InvoiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceConsumerApplication.class, args);
		runThreadToConsumeRecords();
	}

	private static void runThreadToConsumeRecords() {
		int numConsumers = 3;
		String groupId = ApplicationConstant.GROUP_ID_JSON;
		List<String> topics = Arrays.asList(ApplicationConstant.TOPIC_NAME_DEALER, ApplicationConstant.TOPIC_NAME_CAR);
		ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

		final List<InvoiceConsumer> consumers = new ArrayList<>();
		for (int i = 0; i < numConsumers; i++) {
			InvoiceConsumer consumer = new InvoiceConsumer(i, groupId, topics);
			consumers.add(consumer);
			executor.submit(consumer);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (InvoiceConsumer consumer : consumers) {
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
