package top.vncnliu.carve.server.mash.kafka.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
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

    @KafkaListener(topics = "${kafka-topics}")
    public void processMessage(String content) {
        System.out.println(content);
    }


}
