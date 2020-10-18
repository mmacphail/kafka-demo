# Windows / linux compatibility
This docker-compose file use windows syntax for akhq volume configuration : `.\application.yml` instead of `./application.yml`.
Please update this settings accordingly to your OS.

# Config reference for docker
https://docs.confluent.io/current/installation/docker/config-reference.html

# Kafka configuration

You can connect to the tools docker container using the following command: `docker exec -it tools /bin/bash`. This will give you access to all commands like `kafka-topics`.

## List topics
`kafka-topics --bootstrap-server kafka1:9092 --list`

## Create article topic
`kafka-topics --bootstrap-server kafka1:9092 --create --topic article --partitions 3 --replication-factor 3`

## Producer configuration
https://docs.confluent.io/current/installation/configuration/producer-configs.html

## Consumer configuration
https://docs.confluent.io/current/installation/configuration/consumer-configs.html

# Schema registry

## Set article subject full schema compatibiliy
This doesn't work using windows curl, use linux curl (if on Windows, git bash, wsl).
`curl -X PUT -i -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"compatibility": "FULL"}' http://schema-registry:8084/config/article-value`

## Register article schema
`curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{ "schema": "{ \"type\": \"record\", \"name\": \"Article\", \"namespace\": \"eu.macphail.data\", \"fields\": [ { \"name\": \"id\", \"type\": \"long\" }, { \"name\": \"label\", \"type\": \"string\"}, { \"name\": \"color\", \"type\": { \"type\": \"enum\", \"name\": \"Color\", \"symbols\": [ \"BLACK\", \"GREEN\", \"WHITE\", \"YELLOW\", \"ORANGE\", \"BLUE\", \"PINK\" ] }  } ]}" }' http://schema-registry:8084/subjects/article-value/versions`

# Publish avro jar to local maven repository
cd article-avro-schema
gradlew install

# Other examples

## Java Producer example
https://github.com/confluentinc/examples/blob/6.0.0-post/clients/cloud/java/src/main/java/io/confluent/examples/clients/cloud/ProducerExample.java

## Java Consumer example
https://github.com/confluentinc/examples/blob/6.0.0-post/clients/cloud/java/src/main/java/io/confluent/examples/clients/cloud/ConsumerExample.java