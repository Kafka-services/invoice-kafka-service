# invoice-kafka-service
This is the invoice-kafka-service

![alt text](https://github.com/Kafka-services/invoice-kafka-service/blob/main/invoice-kafka-service%20(1).jpg)

### Topic creation
	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic invoice
  kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic dealer
  kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic car

### Check no. of messages
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic itinerary-C19
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic itinerary-C19-COUNTRY_AUS
	kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 --topic itinerary-C19-COUNTRY_US

http://localhost:8070/itinerary/postItineraryAus

		{
			"type": "departure",
			"status": "landed",
			"departure": "2022-01-05T01:06:00.000",
			"arrival": "2022-01-05T01:06:00.000",
			"covid" : "2 dose must"
		}
