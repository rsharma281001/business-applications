package com.rishi.zeebe.spring;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableZeebeClient
@Slf4j
public class ZeebeWorkerApplication {

	private static Logger log = LoggerFactory.getLogger(ZeebeWorkerApplication.class);

	public static void main(final String... args) {
		SpringApplication.run(ZeebeWorkerApplication.class, args);
	}

	private static void logJob(final ActivatedJob job) {
		log.info("complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
				job.getType(), job.getKey(), job.getElementId(), job.getWorkflowInstanceKey(),
				Instant.ofEpochMilli(job.getDeadline()), job.getCustomHeaders(), job.getVariables());
	}

	@ZeebeWorker(type = "initiate-payment")
	public void handleInitialtePayment(final JobClient jobClient, final ActivatedJob job) {
		logJob(job);
		jobClient.newCompleteCommand(job.getKey())
		.send()
		.join();
	}

	@ZeebeWorker(type = "ship-without-insurance")
	public void handleShipWithoutInsuranceJob(final JobClient jobClient, final ActivatedJob job) {
		logJob(job);
		jobClient.newCompleteCommand(job.getKey())
		.send()
		.join();
	}
	
	@ZeebeWorker(type = "ship-with-insurance")
	public void handleShipWithInsuranceJob(final JobClient jobClient, final ActivatedJob job) {
		logJob(job);
		jobClient.newCompleteCommand(job.getKey())
		.send()
		.join();
	}
}
