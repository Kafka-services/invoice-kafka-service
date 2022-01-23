# invoice-kafka-service
This is the invoice-kafka-service

![alt text](https://github.com/Kafka-services/invoice-kafka-service/blob/main/invoice-kafka-service%20(1).jpg)

### Topic creation
	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic invoice
  	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dealer
  	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic car

### Check no. of messages
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic invoice
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic dealer
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic car

http://localhost:9030/postInvoice/
Sample post messages

	{
		"dealerId" : 101,
		"dealerName" : "Nexa",
		"carName" : "BMW",
		"fuelType" : "petrol",
		"engineType" : "1998 cc, 4 Cylinders Inline"
	}

	{
		"dealerId" : 201,
		"dealerName" : "Harsha Toyota",
		"carName" : "BMW",
		"fuelType" : "Diesel",
		"engineType" : "1998 cc, 4 Cylinders Inline"
	}

	{
		"dealerId" : 301,
		"dealerName" : "Mahindra",
		"carName" : "AUDI",
		"fuelType" : "Petrol",
		"engineType" : "2001 cc, 4 Cylinders Inline"
	}
