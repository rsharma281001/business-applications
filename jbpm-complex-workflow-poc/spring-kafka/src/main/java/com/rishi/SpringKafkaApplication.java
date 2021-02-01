package com.rishi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rishi.kafka.service.KafkaProducer;
import com.rishi.model.CreateVMResponse;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}

	@Autowired
	KafkaProducer producer;

	@Override
	public void run(String... arg0) throws Exception {
		CreateVMResponse createVMResponse1 = new CreateVMResponse("i-1234567890abcdef0", "ami-bff32ccc", "small",
				"running", "us-east-1a");
		producer.send(createVMResponse1);

		CreateVMResponse createVMResponse2 = new CreateVMResponse("i-1234567890abcdef0", "ami-bff32ccc", "small",
				"running", "us-east-1a");
		producer.send(createVMResponse2);
	}

}
