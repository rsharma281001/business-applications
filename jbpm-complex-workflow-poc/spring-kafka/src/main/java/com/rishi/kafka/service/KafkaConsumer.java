package com.rishi.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rishi.model.CreateVMResponse;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "${rishi.kafka.topic}")
	public void processMessage(CreateVMResponse createVMResponse) {
		System.out.println("Receiving createVMResponse ===================== " + createVMResponse);
	}
}
