package top.vncnliu.carve.server.mash.kafka.store.chain;

import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import top.vncnliu.server.mash.base.AbsMashEvent;
import top.vncnliu.server.mash.base.ChainRespEventBus;

import java.util.concurrent.ExecutionException;

/**
 * User: liuyq
 * Date: 2018/7/24
 * Description:
 */
public class TestFuture {

    @Test
    void main() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EventBus eventBus = new EventBus();
                    eventBus.register(new TestEventHandler());
                    ChainRespEventBus chainRespEventBus = new ChainRespEventBus(eventBus);
                    eventBus.post(new BackEvent(1111));
                    Object result = chainRespEventBus.exeEvents(
                            new AbsMashEvent[]{
                                new BaseEvent(),
                                new BaseEvent(),
                                new CashEvent()
                            },
                            new AbsMashEvent[]{
                                new BackEvent(1),
                                new BackEvent(2),
                                new BackEvent(3)
                            });
                    System.out.println("result:"+result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(100000);
    }
}
