package top.vncnliu.carve.server.mash.kafka.inventory.handler.rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import top.vncnliu.carve.server.mash.kafka.event.ModifyInventoryEvent;

/**
 * User: liuyq
 * Date: 2018/7/10
 * Description:
 */
@Component
public class OrderRollBackHandler {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Subscribe
    public void createOrder(ModifyInventoryEvent inventoryEvent){
        System.out.println(JSON.toJSONString(inventoryEvent));
        kafkaTemplate.send(inventoryEvent.getSource(),"rollback");
    }
}
