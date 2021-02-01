package com.rishi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.kafka.service.KafkaProducer;
import com.rishi.model.CreateVMResponse;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

	private final KafkaProducer kafkaProducer;

	@Autowired
	KafkaController(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void sendMessageToKafkaTopic(@RequestBody CreateVMResponse createVMResponse) {
		System.out.println("################# Inside Kafka Publish.....###################");
		this.kafkaProducer.send(createVMResponse);
	}

}
