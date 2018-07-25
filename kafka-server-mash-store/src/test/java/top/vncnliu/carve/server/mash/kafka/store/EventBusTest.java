package top.vncnliu.carve.server.mash.kafka.store;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Test;

/**
 * User: liuyq
 * Date: 2018/7/10
 * Description:
 */
public class EventBusTest {

    @Test
    void main(){
        EventBus eventBus = new EventBus();
        eventBus.register(new EvnetHandler());
        eventBus.post(new TestEvent2(1,"a"));
    }

    class EvnetHandler {
        @Subscribe
        public void dododo(TestEvent event){
            System.out.println(JSON.toJSONString(event));
        }
        @Subscribe
        public void dododo(TestEvent2 event){
            System.out.println("2"+JSON.toJSONString(event));
        }
    }

    class TestEvent {
        int a;
        String b;

        public TestEvent(int a, String b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    class TestEvent2 extends TestEvent {

        public TestEvent2(int a, String b) {
            super(a, b);
        }
    }
}
