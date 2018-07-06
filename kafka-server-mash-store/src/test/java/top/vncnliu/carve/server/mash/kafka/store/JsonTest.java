package top.vncnliu.carve.server.mash.kafka.store;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * User: liuyq
 * Date: 2018/7/6
 * Description:
 */
public class JsonTest {

    public static void main(String[] args) throws IOException {
        String json = "{\"a\":1,\"b\":{\"test\":1}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        System.out.println(jsonNode.get("b").toString());
    }
}
