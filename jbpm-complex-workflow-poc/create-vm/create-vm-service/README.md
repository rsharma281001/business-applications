# Steps

# RH PAM / JBPM POC

* Use this to install RH-PAM - https://github.com/jbossdemocentral/rhpam7-install-demo
	* $./init.sh
	* $./target/jboss-eap-7.3/bin/standalone.sh
	* execute http://localhost:8080/business-central

2. Setup Kafka locally - Followed this 
	* https://computingforgeeks.com/configure-apache-kafka-on-ubuntu/	
	* https://otodiginet.com/software/how-to-install-apache-kafka-on-ubuntu-20-04-lts/
	* $ sudo systemctl daemon-reload
	* $ sudo systemctl enable --now zookeeper
	* $ sudo systemctl enable --now kafka
	* $ sudo systemctl status kafka zookeeper

3. How to delete a topic - https://stackoverflow.com/questions/17730905/is-there-a-way-to-delete-all-the-data-from-a-topic-or-delete-the-topic-before-ev
	delete.topic.enable=true
	then, you can run this command:
	$ bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic test

3. 	Red Hat Process Automation Manager 7.9.0 setup complete.  =
	=                                                            =
	=  Start Red Hat Process Automation Manager with:            =
	=                                                            =
	=           ./target/jboss-eap-7.3/bin/standalone.sh         =
	=                                                            =
	=  Log in to Red Hat Process Automation Manager to start     =
	=  developing rules projects:                                =
	=                                                            =
	=  http://localhost:8080/business-central                    =
	=                                                            =
	=    Log in: [ u:pamAdmin / p:redhatpam1!
	 ]                  =
	=                                                            =
	=  http://localhost:8080/rhpam-case-mgmt-showcase            =
	=                                                            =
	=    Others:                                                 =
	=            [ u:kieserver / p:kieserver1! ]                 =
	=            [ u:caseUser / p:redhatpam1! ]                  =
	=            [ u:caseManager / p:redhatpam1! ]               =
	=            [ u:caseSupplier / p:redhatpam1! ]				 =

4.  Imported the CustomWorkItemHandler POC Project from https://access.redhat.com/documentation/en-us/red_hat_process_automation_manager/7.2/html/creating_red_hat_process_automation_manager_business_applications_with_spring_boot/bus-apps-import_business-applications

5. mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
6. Pre-made examples are here https://github.com/kiegroup/jbpm-playground

############## JBPM ####################

7. CHAPTER 5. IMPORTING BUSINESS ASSETS PROJECTS INTO AND DEPLOYING FROM BUSINESS CENTRA - https://access.redhat.com/documentation/en-us/red_hat_process_automation_manager/7.2/html/creating_red_hat_process_automation_manager_business_applications_with_spring_boot/bus-apps-import_business-applications#:~:text=Log%20in%20to%20Business%20Central,Menu%20%E2%86%92%20Design%20%E2%86%92%20Projects.&text=Click%20Import%20and%20confirm%20the,to%20your%20business%20assets%20project.

8. https://docs.jboss.org/jbpm/release/7.48.0.Final/jbpm-docs/html_single/#_develop_your_business_application

9. If you want to create Custom workItem handelers
	mvn archetype:generate -DarchetypeGroupId=org.jbpm -DarchetypeArtifactId=jbpm-workitems-archetype -DarchetypeVersion=7.45.0.Final -Dversion=1.0.0-SNAPSHOT -DgroupId=com.rishi -DartifactId=policy-workitem-handler -DclassPrefix=PolicyWorkItemHandler

	mvn archetype:generate -DarchetypeGroupId=org.jbpm -DarchetypeArtifactId=jbpm-workitems-archetype -DarchetypeVersion=7.45.0.Final -Dversion=1.0.0-SNAPSHOT -DgroupId=com.rishi -DartifactId=policy-workitem-handler -DclassPrefix=PolicyWorkItemHandler

10. 