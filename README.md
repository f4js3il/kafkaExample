Apache Kafka Publisher Example using SpringBoot

start zookeeper.start bat file like below
zookeeper-server-start.bat C:\Softwares\Kafka\kafka_2.12-3.1.0\config\zookeeper.properties

start kafka server
kafka-server-start.bat C:\Softwares\Kafka\kafka_2.12-3.1.0\config\server.properties

Create Topic:
kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 -topic shalu

Produce a message
kafka-console-producer.bat --broker-list localhost:9092 --topic shalu

Consume a message
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic shalu
