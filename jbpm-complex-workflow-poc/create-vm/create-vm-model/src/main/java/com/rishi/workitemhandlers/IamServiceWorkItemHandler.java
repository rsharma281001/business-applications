/**
 * 
 */
package com.rishi.workitemhandlers;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.rishi.model.IamServiceResponse;

/**
 * @author rishis
 *
 */
//@Component("IamServiceWorkItemHandler")
public class IamServiceWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		
		System.out.println("Entering into IamServiceWorkItemHandler...");

		IamServiceResponse iamServiceResponseLocal = (IamServiceResponse) workItem.getParameter("iamServiceResponse");
		System.out.println("IamServiceResponse is ::: " + iamServiceResponseLocal.toString());
		
		// iamCheckValue will be Passed only when its a developer
		String iamCheckValue = "Failed";
		if(iamServiceResponseLocal.getIamResponse().getRole().equals("developer")) {
			iamCheckValue = "Passed";
		}
		System.out.println("iamCheckValue is :: " + iamCheckValue);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("iamCheck", iamCheckValue);

		manager.completeWorkItem(workItem.getId(), map);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Aborting the IamServiceWorkItemHandler...");

	}

}
