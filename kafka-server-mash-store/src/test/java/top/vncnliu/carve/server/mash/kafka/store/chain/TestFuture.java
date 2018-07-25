package top.vncnliu.carve.server.mash.kafka.store.chain;

import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import top.vncnliu.server.mash.base.AbsMashEvent;
import top.vncnliu.server.mash.base.BackEvent;
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
        exeEvents();
        exeEvents();
        Thread.sleep(1000);
    }

    private void exeEvents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EventBus eventBus = new EventBus();
                    eventBus.register(new TestEventHandler());
                    EventBus eventBus2 = new EventBus();
                    eventBus2.register(new TestEventHandler());
                    ChainRespEventBus chainRespEventBus = new ChainRespEventBus(eventBus,eventBus2);
                    /*BackEvent backEvent = new BackEvent(1111);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);
                    eventBus.post(backEvent);*/
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
    }
}
