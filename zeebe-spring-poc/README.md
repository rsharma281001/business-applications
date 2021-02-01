# Zeebe

1. Architecture - https://docs.camunda.io/assets/images/zeebe-architecture-67c608106ddc1c9eaa686a5a268887f9.png

2. The local installation was failing using package. So had to use Docker installation https://docs.camunda.io/docs/product-manuals/zeebe/deployment-guide/docker/install
   $ docker run --name zeebe -p 26500-26502:26500-26502 camunda/zeebe:latest

   	Exposed ports#
		26500: Gateway API
		26501: Command API (gateway-to-broker)
		26502: Internal API (broker-to-broker)

	Volumes#
		The default data volume is under /usr/local/zeebe/data. It contains all data which should be persisted.

	Configuration#
		The Zeebe configuration is located at /usr/local/zeebe/config/application.yaml. The logging configuration is located at /usr/local/zeebe/config/log4j2.xml.

	Available environment variables:
		ZEEBE_LOG_LEVEL - sets the log level of the Zeebe Logger (default: info).
		ZEEBE_BROKER_NETWORK_HOST - sets the host address to bind to instead of the IP of the container.
		ZEEBE_BROKER_CLUSTER_INITIALCONTACTPOINTS - sets the contact points of other brokers in a cluster setup.

3. https://docs.camunda.io/docs/product-manuals/zeebe/deployment-guide/getting-started/deploy-a-workflow - We offer Operate free of charge for unrestricted non-production use because we think it's a great tool for getting familiar with Zeebe and building initial proofs-of-concept. And at this time, Operate is available for non-production use only. In the future, we'll offer an Operate enterprise license that allows for production use, too.

4. To do a POC, we need
	- Zeebe Broker
	- Camunda Operate - Elasticsearch is also what Camunda Operate uses to store data, so to run Operate, you need to enable the Elasticsearch exporter in Zeebe and run an instance of Elasticsearch.
	- Elastic Operator - Zeebe itself doesn't store historic data related to your workflow instances. If you want to keep this data for auditing or for analysis, you need to export to another storage system. Zeebe does provide an easy-to-use exporter interface, and it also offers an Elasticsearch exporter out of the box

5. First, copy the following lines into a new file getting-started.yaml file (in the config directory of the Zeebe broker). https://docs.camunda.io/docs/product-manuals/zeebe/deployment-guide/getting-started/deploy-a-workflow
	- So i will copy the getting-started.yaml into 
   Note - This is complicated so i am going the way for Docker compose

 6. Docker compose# (https://docs.camunda.io/docs/product-manuals/zeebe/deployment-guide/getting-started/deploy-a-workflow)
If you are using Docker and zeebe-docker-compose then follow the instructions in the README file in the operate directory of that repository to start Zeebe and Operate. Once you have done that, skip the following section, and continue from check the status of the broker.
  	- https://github.com/zeebe-io/zeebe-docker-compose/tree/master/operate
  		rishis@rs:~/development/git-code/zeebe/zeebe-docker-compose/operate$ sudo docker-compose up  
 7. Check the status of the broker
 		rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure status
			Cluster size: 1
			Partitions count: 1
			Replication factor: 1
			Gateway version: 0.25.1
			Brokers:
			  Broker 0 - 172.18.0.3:26501
			    Version: 0.25.1
			    Partition 1 : Leader, Healthy
8. Deploy the worklfow#
	After we run this command then we can check the status in Camunda Operate
	rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure deploy ~/development/git-code/zeebe/order-process.bpmn 
	{
	  "key": 2251799813685250,
	  "workflows": [
	    {
	      "bpmnProcessId": "order-process",
	      "version": 1,
	      "workflowKey": 2251799813685249,
	      "resourceName": "/home/rishis/development/git-code/zeebe/order-process.bpmn"
	    }
	  ]
	}
9. Create and complete workflow instances
	We're going to create 2 workflow instances for this tutorial: one with an order value less than $100 and one with an order value greater than or equal to $100 so that we can see our XOR gateway in action.
		- The first workflow instance we just created represents a single customer order with orderId 1234 and orderValue 99 (or, $99). 
			rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure create instance order-process --variables '{"orderId": "1234", "orderValue":99}'
			{
			  "workflowKey": 2251799813685249,
			  "bpmnProcessId": "order-process",
			  "version": 1,
			  "workflowInstanceKey": 2251799813685251
			}
		- The second workflow instance we just created represents a single customer order with orderId 2345 and orderValue 100 (or, $100).
			rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure create instance order-process --variables '{"orderId": "2345", "orderValue":100}'
			{
			  "workflowKey": 2251799813685249,
			  "bpmnProcessId": "order-process",
			  "version": 1,
			  "workflowInstanceKey": 2251799813685259
			}
10. If you see the Camunda Operate, you will see 2 workflow instances but both of them should be waiting for the *Initiate Payment*. The  workflow instance can't move past this first task until we create a job worker to complete initiate-payment jobs. So we will creare job worker.
	Note :- We have two instances currently waiting at our Initiate Payment task, which means that Zeebe has created two jobs with type initiate-payment.
	
	zbctl provides a command to spawn simple job workers using an external command or script. The job worker will receive the payload for every job as a JSON object on stdin and must also return its result as JSON object on stdout if it handled the job successfully.

	In a real-word use case, you probably won't manually create workflow instances using the Zeebe CLI. Rather, a workflow instance would be created programmatically in response to some business event, such as a message sent to Zeebe after a customer places an order. And instances might be created at very large scale if, for example, many customers were placing orders at the same time due to a sale. We're using the CLI here just for simplicity's sake.
		
	- Now create a job worker that will work on the initiate-payment job
		rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure create worker initiate-payment --handler cat
		2021/01/15 13:02:57 Activated job 2251799813685266 with variables {"orderId":"2345","orderValue":100}
		2021/01/15 13:02:57 Activated job 2251799813685258 with variables {"orderId":"1234","orderValue":99}
		2021/01/15 13:02:57 Handler completed job 2251799813685266 with variables {"orderId":"2345","orderValue":100}
		2021/01/15 13:02:57 Handler completed job 2251799813685258 with variables {"orderId":"1234","orderValue":99}


11. In Camunda Operate, you should see that the workflow instances have advanced to the intermediate message catch event and are waiting there
	- The workflow instances will wait at the intermediate message catch event until a message is received by Zeebe and correlated to the instances. Messages can be published using Zeebe clients, and it's also possible for Zeebe to connect to a message queue such as Apache Kafka and correlate messages published there to workflow instances.

	- zbctl also supports message publishing, so we'll continue to use it in our demo. We use the command to publish and correlate a message. Remember orderId is the correlation key we set in the Modeler when configuring the message event.
		./zbctl --insecure publish message "payment-received" --correlationKey="1234"
			rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure publish message "payment-received" --correlationKey="1234"
			{
			  "key": 2251799813685300
			}


		./zbctl --insecure publish message "payment-received" --correlationKey="2345"
			rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure publish message "payment-received" --correlationKey="2345"
			{
			  "key": 2251799813685307
			}

12. Go ahead and open a Terminal window and create a job worker for the ship-without-insurance job type.
	rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure create worker ship-without-insurance --handler cat
	2021/01/15 13:15:20 Activated job 2251799813685306 with variables {"orderId":"1234","orderValue":99}
	2021/01/15 13:15:20 Handler completed job 2251799813685306 with variables {"orderId":"1234","orderValue":99}

13. Go ahead and open a Terminal window and create a job worker for the ship-with-insurance job type.
	rishis@rs:~/development/tools/zeebe/zeebe-broker-0.26.0/bin$ ./zbctl --insecure create worker ship-with-insurance --handler cat
	2021/01/15 13:17:20 Activated job 2251799813685313 with variables {"orderId":"2345","orderValue":100}
	2021/01/15 13:17:21 Handler completed job 2251799813685313 with variables {"orderId":"2345","orderValue":100}

14. My understansing
======================
	- How to trigger a workflow using a rest api
	- D


Learning Kafka
=============
	- https://www.youtube.com/watch?v=B5j3uNBH8X4
	- Java is the native language of Apache Kafka, the language library that ships with Kafka is Java. Rest of the languages like Python, GO are all wrappers to make the communication working.
	- Even REST Proxy is there
