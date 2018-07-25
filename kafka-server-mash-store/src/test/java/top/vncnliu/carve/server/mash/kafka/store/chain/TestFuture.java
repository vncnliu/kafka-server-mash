package top.vncnliu.carve.server.mash.kafka.store.chain;

import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import top.vncnliu.server.mash.base.AbsMashEvent;
import top.vncnliu.server.mash.base.BaseEvent;
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
                    Object result = chainRespEventBus.exeEvents(
                            new AbsMashEvent[]{
                            new BaseEvent(),
                            new CashEvent(),
                            new BaseEvent()},
                            new AbsMashEvent[]{
                            null,
                            null,
                            null});
                    System.out.println("result:"+result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);
    }
}
