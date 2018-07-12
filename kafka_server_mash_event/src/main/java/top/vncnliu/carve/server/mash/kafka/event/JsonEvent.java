package top.vncnliu.carve.server.mash.kafka.event;

import lombok.Data;

/**
 * User: liuyq
 * Date: 2018/7/10
 * Description:
 */
@Data
public class JsonEvent {
    private String data;
    private String eventName;
    /**
     * 事件类型
     * 0 执行 1 回滚
     */
    private Integer type;
}
