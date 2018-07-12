package top.vncnliu.carve.server.mash.kafka.inventory;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;
import top.vncnliu.carve.server.mash.kafka.event.JsonEvent;
import top.vncnliu.carve.server.mash.kafka.inventory.handler.commit.OrderHandler;
import top.vncnliu.carve.server.mash.kafka.inventory.handler.rollback.OrderRollBackHandler;

/**
 * User: liuyq
 * Date: 2018/7/5
 * Description:
 */
@SpringBootApplication
@RestController
@EnableAutoConfiguration
@Slf4j
public class ServerMashInventory {

    @Bean
    @Qualifier("commit")
    public EventBus registerCommitEventBus(){
        EventBus eventBus = new EventBus();
        eventBus.register(new OrderHandler());
        return eventBus;
    }

    @Bean
    @Qualifier("rollback")
    public EventBus registerRollBackEventBus(){
        EventBus eventBus = new EventBus();
        eventBus.register(new OrderRollBackHandler());
        return eventBus;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerMashInventory.class);
    }

    @Autowired
    @Qualifier("commit")
    private EventBus commitEventBus;
    @Autowired
    @Qualifier("rollback")
    private EventBus rollBackEventBus;

    @KafkaListener(topics = "${kafka-topics}")
    public void processMessage(String content) {
        log.debug(content);
        JsonEvent jsonEvent = JSON.parseObject(content,JsonEvent.class);
        try {
            if(jsonEvent.getType()==0){
                commitEventBus.post(JSON.parseObject(jsonEvent.getData(),Class.forName(jsonEvent.getEventName())));
            } else if(jsonEvent.getType()==1){
                rollBackEventBus.post(JSON.parseObject(jsonEvent.getData(),Class.forName(jsonEvent.getEventName())));
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(),e);
        }
    }

}
