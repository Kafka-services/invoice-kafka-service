package com.invoice.externalfeed.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.externalfeed.kafka.producer.ProducerRecordServiceImpl;
import com.invoice.externalfeed.model.InvoiceModel;

@RestController
public class ExternalFeedController {

	@PostMapping("/postInvoice")
	public ResponseEntity postInvoice(@RequestBody InvoiceModel model) {
		ProducerRecordServiceImpl serviceImpl = new ProducerRecordServiceImpl();
		serviceImpl.produceMessage(model);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
