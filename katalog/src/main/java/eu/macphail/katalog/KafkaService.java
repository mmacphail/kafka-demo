package eu.macphail.katalog;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${kafka.url}")
    private String kafkaUrl;

    @Value("${kafka.article.topic}")
    private String topic;

    private Producer<Long, Article> producer;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "0");

        producer = new KafkaProducer<>(props);
    }

    @PreDestroy
    public void cleanup() {
        producer.close();
    }

    public void sendArticle(Article article) {
        try {
            ProducerRecord<Long, Article> record = new ProducerRecord<>(topic, article.getId(), article);
            Future<RecordMetadata> metadata = producer.send(record);
            RecordMetadata recordMetadata = metadata.get();

            logger.info("Persisted article at offset {}", recordMetadata.offset());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
