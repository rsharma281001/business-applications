package com.rishi.service;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.VariableDesc;
import org.kie.server.springboot.jbpm.ContainerAliasResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.CreateVMRequest;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
	private ProcessService processService;

	@Autowired
	private RuntimeDataService runtimeDataService;

	@Autowired
	private ContainerAliasResolver aliasResolver;

	private String containerAlias = "create-vm-kjar";
	private String processId = "create-vm-kjar.create-vm";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@PostMapping("/vm-service/createVM")
	@ResponseBody
	public ResponseEntity<String> createVM(@RequestBody CreateVMRequest createVMRequest) throws Exception {

		System.out.println("Starting the Create VM Workflow with request :: " + createVMRequest);
		
		Map<String, Object> processInputs = new HashMap<>();
		processInputs.put("createVMRequest", createVMRequest);

		Long processInstanceId = processService.startProcess(aliasResolver.latest(containerAlias), processId,
				processInputs);

		for (VariableDesc var : runtimeDataService.getVariablesCurrentState(processInstanceId)) {
			//if (var.getVariableId().equals("output"))
			//return ResponseEntity.ok(var.getNewValue());
			return ResponseEntity.ok(var.getNewValue());
		}
		return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Processing Error");
	}
}