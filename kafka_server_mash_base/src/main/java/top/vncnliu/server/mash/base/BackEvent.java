package top.vncnliu.server.mash.base;

import lombok.AllArgsConstructor;
import lombok.Data;

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
