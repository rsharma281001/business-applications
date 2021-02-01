/**
 * 
 */
package com.rishi.workitemhandlers;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import com.rishi.model.BluePrintServiceResponse;

/**
 * @author rishis
 *
 */
//@Component("BluePrintServiceWorkItemHandler")
public class BluePrintServiceWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		System.out.println("Entering into BluePrintServiceWorkItemHandler...");

		BluePrintServiceResponse bluePrintServiceResponseLocal = (BluePrintServiceResponse) workItem
				.getParameter("bluePrintServiceResponse");
		System.out.println("BluePrintServiceWorkItemHandler is ::: " + bluePrintServiceResponseLocal.toString());

		// iamCheckValue will be Passed only when its a developer
		String bluePrintCheckValue = "Failed";
		if (bluePrintServiceResponseLocal.getBlueprint().equals("create-vm")) {
			bluePrintCheckValue = "Passed";
		}
		System.out.println("bluePrintCheckValue is :: " + bluePrintCheckValue);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bluePrintCheck", bluePrintCheckValue);

		manager.completeWorkItem(workItem.getId(), map);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Aborting the BluePrintServiceWorkItemHandler...");

	}

}
