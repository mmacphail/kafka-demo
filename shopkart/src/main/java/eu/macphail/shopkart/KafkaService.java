package eu.macphail.shopkart;

import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${kafka.consumer.client.id}")
    private String clientID;

    @Value("${kafka.consumer.group.id}")
    private String groupID;

    @Value("${kafka.url}")
    private String kafkaUrl;

    @Value("${kafka.article.topic}")
    private String topic;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ArticleService articleService;

    @PostConstruct
    public void init() {
        taskExecutor.execute(this::pollForMessages);
    }

    public void pollForMessages() {
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientID);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonDeserializer");
        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, Article.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

        try (KafkaConsumer<Long, Article> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    consumer.commitSync();
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

                }
            });

            while (true) {
                ConsumerRecords<Long, Article> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<Long, Article> record : records) {
                    Article article = record.value();
                    logger.info("New article received: {}", article);

                    articleService.addNewArticle(article);
                    consumer.commitAsync();
                }
            }
        }
    }
}
