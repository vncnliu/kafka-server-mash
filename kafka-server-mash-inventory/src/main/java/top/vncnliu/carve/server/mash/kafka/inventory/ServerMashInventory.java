package top.vncnliu.carve.server.mash.kafka.inventory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: liuyq
 * Date: 2018/7/5
 * Description:
 */
@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class ServerMashInventory {

    public static void main(String[] args) {
        SpringApplication.run(ServerMashInventory.class);
    }

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @KafkaListener(topics = "${kafka-topics}")
    public void processMessage(String content) {
        System.out.println(content);
        JSONObject jsonObject = JSON.parseObject(content);
        JSONObject result = new JSONObject();
        result.put("request_key",jsonObject.getString("request_key"));
        result.put("result","i am from inventory");
        kafkaTemplate.send(jsonObject.getString("source"),result.toJSONString());
    }


}
