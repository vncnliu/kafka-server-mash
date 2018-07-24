package top.vncnliu.carve.kafka.server.mash.server;

import lombok.Data;

/**
 * User: liuyq
 * Date: 7/14/18
 * Description:
 */
@Data
public class PackMessage {

    private int length;

    private String data;

}
