package top.vncnliu.carve.server.mash.kafka.event;

import lombok.Data;

/**
 * User: liuyq
 * Date: 2018/7/10
 * Description:
 */
@Data
public class ModifyInventoryEvent extends BaseEvent {
    private Integer storeId;
    private Integer nums;
}
