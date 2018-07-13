package top.vncnliu.carve.server.mash.kafka.store;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: liuyq
 * Date: 2018/7/5
 * Description:
 */
@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class ServerMashStore {

    public static void main(String[] args) {
        //SpringApplication.run(ServerMashStore.class);
        ContainerProperties containerProps = new ContainerProperties("topic1");
        containerProps.setGroupId("test");
        containerProps.setMessageListener((MessageListener<String, String>) data -> {
            System.out.println(data.value());
        });
        DefaultKafkaConsumerFactory<String, String> cf =
                new DefaultKafkaConsumerFactory<>(new HashMap<>());
        KafkaMessageListenerContainer<String, String> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container.start();
    }

    public static ConcurrentHashMap<String,DeferredResult> requestHold = new ConcurrentHashMap<>();

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "${mash-kafka.topics}", partitions = { "0", "1" })})
    public void processMessage(String content) {
        System.out.println("0,1||"+content);
        /*try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String requestKey = jsonNode.get("request_key").asText();
            requestHold.get(requestKey).setResult(content);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @KafkaListener(topicPartitions = { @TopicPartition(topic = "${mash-kafka.topics}", partitions = { "1", "2" })})
    public void processMessage2(String content) {
        /*try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String requestKey = jsonNode.get("request_key").asText();
            requestHold.get(requestKey).setResult(content);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("1,2||"+content);
    }

/*
    @KafkaListener(topics = {"${mash-kafka.topics}"})
    public void processMessage3(String content) {
        */
/*try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String requestKey = jsonNode.get("request_key").asText();
            requestHold.get(requestKey).setResult(content);
        } catch (IOException e) {
            e.printStackTrace();
        }*//*

        System.out.println("*||"+content);
    }
*/


    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private Config config;

    @PostMapping("create")
    public DeferredResult<String> createOrder(){
        String requestKey = UUID.randomUUID().toString();
        DeferredResult<String> deferredResult = new DeferredResult<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("source",config.getTopics());
        jsonObject.put("request_key",requestKey);
        kafkaTemplate.send(config.getInventoryTopics(),jsonObject.toJSONString());
        requestHold.put(requestKey,deferredResult);
        return deferredResult;
    }
}
