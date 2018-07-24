package top.vncnliu.carve.server.mash.kafka.store.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * User: liuyq
 * Date: 7/15/18
 * Description:
 */
public class ProducerTest {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        Producer<String, Long> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
            while (true){
                Thread.sleep(1000);
                for (int i = 0; i < 10; i++) {
                    String msg = "Message " + i;
                    producer.send(new ProducerRecord<>("test_topics_"+i, System.currentTimeMillis()));
                    System.out.println("Sent:" + msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            producer.close();
        }

    }
}
