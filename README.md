# Config reference for docker
https://docs.confluent.io/current/installation/docker/config-reference.html

# Kafka configuration

## List topics
kafka-topics --bootstrap-server kafka1:9092 --list

## Create article topic
kafka-topics --bootstrap-server kafka1:9092 --create --topic article --partitions 3 --replication-factor 3

## Producer configuration
https://docs.confluent.io/current/installation/configuration/producer-configs.html