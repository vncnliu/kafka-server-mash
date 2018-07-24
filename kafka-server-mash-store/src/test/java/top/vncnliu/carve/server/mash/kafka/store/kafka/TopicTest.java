package top.vncnliu.carve.server.mash.kafka.store.kafka;

import kafka.admin.AdminUtils;
import kafka.admin.RackAwareMode;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.security.JaasUtils;

import java.util.Properties;

/**
 * User: liuyq
 * Date: 7/15/18
 * Description:
 */
public class TopicTest {
    public static void main(String[] args) {
        ZkUtils zkUtils = ZkUtils.apply("localhost:2181", 30000, 30000, JaasUtils.isZkSecurityEnabled());
        // 创建一个单分区单副本名为t1的topic
        for (int i = 100; i < 200; i++) {
            try {
                AdminUtils.createTopic(zkUtils, "test_topics_"+i, 1, 1, new Properties(), RackAwareMode.Enforced$.MODULE$);
            } catch (TopicExistsException e) {
                System.out.println("exit topic "+i);
            }
        }
        zkUtils.close();
    }
}
