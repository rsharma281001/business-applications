package com.rishi.service;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.VariableDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.Employee;

@SpringBootApplication
@RestController
public class Application {

	@Autowired
    private ProcessService processService;

	@Autowired 
	private RuntimeDataService runtimeDataService;
	 
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @GetMapping("/fetchEmployee/{id}")
    public ResponseEntity<String> sayHello(@PathVariable String id) throws Exception {
    	Employee emp = new Employee();
    	emp.setId(id);
    	emp.setName("hardcodedname");
    	
    	Map<String, Object> vars = new HashMap<>();
    	vars.put("emp", emp);
    	Long processInstanceId = processService.startProcess("custom-workitemhandler-rest-kjar-1_0-SNAPSHOT", "CustomWorkItemHandlerRest.CustomWIHRest", vars);
    	for ( VariableDesc var: runtimeDataService.getVariablesCurrentState(processInstanceId) ) {
    		if ( var.getVariableId().equals("output"))
    	    	return ResponseEntity.ok(var.getNewValue());
    	}
    	return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("Processing Error");
    }    
}