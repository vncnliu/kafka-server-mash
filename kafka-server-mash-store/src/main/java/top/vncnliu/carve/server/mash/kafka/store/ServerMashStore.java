package top.vncnliu.carve.server.mash.kafka.store;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
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
        SpringApplication.run(ServerMashStore.class);
    }

    public static ConcurrentHashMap<String,DeferredResult> requestHold = new ConcurrentHashMap<>();

    @KafkaListener(topics = "${mash-kafka.topics}")
    public void processMessage(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(content);
            String requestKey = jsonNode.get("request_key").asText();
            requestHold.get(requestKey).setResult(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
