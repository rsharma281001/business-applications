package com.service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.BluePrintServiceResponse;
import com.rishi.model.CreateVMRequest;
import com.rishi.model.CreateVMResponse;
import com.rishi.model.EmailResponse;
import com.rishi.model.IamResponse;
import com.rishi.model.IamServiceResponse;
import com.rishi.model.PolicyServiceResponse;

@RestController
public class MainController {

	/*@GetMapping(value = "/policy/api/policy/{size}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PolicyServiceResponse verifyPolicy(@PathVariable("size") String size) {
		System.out.println("Inside Policy As Code Service...");
		return new PolicyServiceResponse(true);
	}*/
	
	/*@GetMapping(value = "/iam-service/api/role/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public IamServiceResponse fetchRole(@RequestBody CreateVMRequest createVMRequest) throws IOException {
		System.out.println("Inside IAM-Service...");
		System.out.println("createVMRequestObj :: " + createVMRequest.toString());
		IamResponse iamResponse = new IamResponse(1, "developer", "Rishi", "Sharma", "rishis");
		IamServiceResponse iamServiceResponse = new IamServiceResponse("success", iamResponse);
		return iamServiceResponse;
	}*/
	
	@GetMapping(value = "/iam-service/api/role/{userName}")
	@ResponseBody
	public IamServiceResponse getRole(@PathVariable String userName) {
		System.out.println("Inside IAM-Service...");
		System.out.println("Received UserName :: " + userName);
		IamResponse iamResponse = new IamResponse(1, "developer", "Rishi", "Sharma", "rishis");
		IamServiceResponse iamServiceResponse = new IamServiceResponse("success", iamResponse);
		return iamServiceResponse;
	}
	
	@GetMapping(value = "/blueprint-service/api/blueprint")
	@ResponseBody
	public BluePrintServiceResponse fetchBlueprints() {
		System.out.println("Inside BluePrint Service...");
		return new BluePrintServiceResponse("create-vm");
	}

	@PostMapping(value = "/policy-service/api/policy", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public PolicyServiceResponse checkPolicy(@RequestBody CreateVMRequest createVMRequest) {
		System.out.println("Inside Check Policy Service...");
		System.out.println("createVMRequest :: " + createVMRequest.toString());
		return new PolicyServiceResponse(true);
	}

	@PostMapping(value = "/vra-service/api/vm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public CreateVMResponse createVM(@RequestBody CreateVMRequest createVMRequest) {
		System.out.println("Inside VRA Service..." + createVMRequest.toString());
		return new CreateVMResponse("i-1234567890abcdef0", "ami-bff32ccc", "small", "running", "us-east-1a");
	}
	
	@PostMapping(value = "/email-service/api/sendemail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public EmailResponse sendEmail(@RequestBody String emailMessage) {
		System.out.println("Inside Email Service...::: " + emailMessage);
		return new EmailResponse("Message Sent Successfully");
	}

}
