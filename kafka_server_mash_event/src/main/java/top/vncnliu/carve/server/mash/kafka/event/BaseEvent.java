package top.vncnliu.carve.server.mash.kafka.event;

import lombok.Data;

/**
 * User: liuyq
 * Date: 2018/7/10
 * Description:
 */
@Data
public class BaseEvent {
    /**
     * 来源，需要回复时用
     */
    private String source;
}
