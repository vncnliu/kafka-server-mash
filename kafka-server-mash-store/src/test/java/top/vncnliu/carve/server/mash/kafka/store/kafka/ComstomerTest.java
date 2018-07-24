package top.vncnliu.carve.server.mash.kafka.store.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * User: liuyq
 * Date: 7/15/18
 * Description:
 */
public class ComstomerTest {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");

        KafkaConsumer<String, Long> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topics = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            topics.add("test_topics_"+i);
        }
        kafkaConsumer.subscribe(topics);
        while (true) {
            ConsumerRecords<String, Long> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, Long> record : records) {
                System.out.printf("offset = %d, value = %s \n", record.offset(), System
                        .currentTimeMillis()-record.value());
            }
        }
    }
}
