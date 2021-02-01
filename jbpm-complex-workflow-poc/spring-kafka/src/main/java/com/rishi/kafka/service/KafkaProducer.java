package com.rishi.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.rishi.model.CreateVMResponse;

@Service
public class KafkaProducer {
	@Autowired
	private KafkaTemplate<String, CreateVMResponse> kafkaTemplate;
	
	@Value("${rishi.kafka.topic}")
	String kafkaTopic = "create-vm";
	
	public void send(CreateVMResponse createVMResponse) {
	    System.out.println("Sending createVMResponse -====================" + createVMResponse);
	    kafkaTemplate.send(kafkaTopic, createVMResponse);
	}
}
