package top.vncnliu.carve.server.mash.kafka.store;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * User: liuyq
 * Date: 2018/7/5
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "mash-kafka")
public class Config {

    private String topics;
    private String inventoryTopics;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getInventoryTopics() {
        return inventoryTopics;
    }

    public void setInventoryTopics(String inventoryTopics) {
        this.inventoryTopics = inventoryTopics;
    }
}
