package com.rishi.zeebe.spring;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import io.zeebe.spring.client.annotation.ZeebeDeployment;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableZeebeClient
@EnableScheduling
@ZeebeDeployment(classPathResources = "order-process.bpmn")
@Slf4j
public class ZeebeClientApplication {

	private static Logger log = LoggerFactory.getLogger(ZeebeClientApplication.class);

	public static void main(final String... args) {
		SpringApplication.run(ZeebeClientApplication.class, args);
	}

	@Autowired
	private ZeebeClientLifecycle client;

	@Scheduled(fixedRate = 50000000000L)
	public void startProcesses() {
		if (!client.isRunning()) {
			return;
		}
		
		String uuIdStr = UUID.randomUUID().toString();

		final WorkflowInstanceEvent wfInstanceEvent = client.newCreateInstanceCommand()
				.bpmnProcessId("order-process")
				.latestVersion()
				//.variables("{\"orderId\": \"" + UUID.randomUUID().toString() + "\"}")
				.variables("{\"orderId\":\""+uuIdStr+"\",\"orderValue\":99}")
				.send()
				.join();

		log.info("started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
				wfInstanceEvent.getWorkflowKey(), wfInstanceEvent.getBpmnProcessId(), wfInstanceEvent.getVersion(), wfInstanceEvent.getWorkflowInstanceKey());
	}
}
