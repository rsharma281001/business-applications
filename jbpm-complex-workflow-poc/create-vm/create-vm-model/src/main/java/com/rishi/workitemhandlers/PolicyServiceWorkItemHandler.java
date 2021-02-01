/**
 * 
 */
package com.rishi.workitemhandlers;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.rishi.model.PolicyServiceResponse;

/**
 * @author rishis
 *
 */
//@Component("PolicyServiceWorkItemHandler")
public class PolicyServiceWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Entering into PolicyServiceWorkItemHandler...");

		PolicyServiceResponse PolicyServiceResponseLocal = (PolicyServiceResponse) workItem.getParameter("policyServiceResponse");
		System.out.println("policyServiceResponse ::: " + PolicyServiceResponseLocal.toString());

		// policyCheck will be Passed when allow is true
		String policyCheckValue = "Failed";
		if(PolicyServiceResponseLocal.getAllow()) {
			policyCheckValue = "Passed";
		}
		System.out.println("policyCheckValue is :: " + policyCheckValue);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("policyCheck", policyCheckValue);
		manager.completeWorkItem(workItem.getId(), map);

		System.out.println("Exiting from PolicyServiceWorkItemHandler.....");
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Aborting the PolicyServiceWorkItemHandler...");

	}

}
