package top.vncnliu.carve.server.mash.kafka.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

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

    @KafkaListener(topics = "${kafka-topics}")
    public void processMessage(String content) {
        System.out.println(content);
    }

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private Config config;

    @PostMapping("create")
    public DeferredResult<String> createOrder(){
        DeferredResult<String> deferredResult = new DeferredResult<>();
        //kafkaTemplate.send(config.getInventoryTopics(),"success");
        return deferredResult;
    }
}
