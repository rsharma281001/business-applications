/**
 * 
 */
package com.rishi;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

/**
 * @author rishi
 *
 */
public class CustomWIH implements WorkItemHandler {

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Entering into com.rishi.CustomWIH.executeWorkItem()...");
		Employee employee = (Employee) workItem.getParameter("emp");
		employee.setName("Rishi Sharma");
		System.out.println("Changing the name to :: " + employee.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp", employee);
		manager.completeWorkItem(workItem.getId(), map);
		System.out.println("Exiting from com.rishi.CustomWIH.executeWorkItem()...");
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Aborting the WorkItem");
		
	}

}
