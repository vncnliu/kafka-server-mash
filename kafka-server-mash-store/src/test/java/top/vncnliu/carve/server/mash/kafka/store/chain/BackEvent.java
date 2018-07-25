package top.vncnliu.carve.server.mash.kafka.store.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.vncnliu.server.mash.base.AbsMashEvent;

/**
 * User: liuyq
 * Date: 2018/7/25
 * Description:
 */
@Data
@AllArgsConstructor
public class BackEvent extends AbsMashEvent {
    private int id;
}
