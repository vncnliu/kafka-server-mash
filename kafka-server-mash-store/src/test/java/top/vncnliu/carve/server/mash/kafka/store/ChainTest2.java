package top.vncnliu.carve.server.mash.kafka.store;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import top.vncnliu.carve.server.mash.kafka.event.BaseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * User: liuyq
 * Date: 2018/7/9
 * Description:
 */
public class ChainTest2 {

    @Test
    void main() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //exeinone();
                    Object result = exeEvents(new BaseEvent[]{
                            new BaseEvent("1", new CompletableFuture()),
                            new BaseEvent("2", new CompletableFuture()),
                            new BaseEvent("3", new CompletableFuture())});
                    System.out.println("result"+result);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);
    }

    private void exeinone() {
        EventBus eventBus = new EventBus();
        eventBus.register(new BaseEventHandler());

        BaseEvent baseEvent1 = new BaseEvent("1",new CompletableFuture());
        BaseEvent baseEvent1_b = new BaseEvent("bak 1",new CompletableFuture());
        BaseEvent baseEvent2 = new BaseEvent("2",new CompletableFuture());
        BaseEvent baseEvent2_b = new BaseEvent("bak 2",new CompletableFuture());
        BaseEvent baseEvent3 = new BaseEvent("3",new CompletableFuture());
        BaseEvent baseEvent3_b = new BaseEvent("bak 3",new CompletableFuture());
        eventBus.post(baseEvent1);
        baseEvent1.completableFuture.thenAccept(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("accept"+o);
                eventBus.post(baseEvent2);
            }
        });
        baseEvent2.completableFuture.thenAccept(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("accept"+o);
                eventBus.post(baseEvent3);
            }
        });
        baseEvent3.completableFuture.thenAccept(new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println("accept"+o);
                System.out.println("end");
            }
        });
    }

    Object exeEvents(BaseEvent[] baseEvents) throws ExecutionException, InterruptedException {
        EventBus eventBus = new EventBus();
        eventBus.register(new BaseEventHandler());

        BaseEvent end = new BaseEvent("end",new CompletableFuture());

        List<BaseEvent> backEvent = new ArrayList<>();

        for (int i = 0; i < baseEvents.length; i++) {
            BaseEvent now = baseEvents[i];
            BaseEvent next;
            if(i==baseEvents.length-1){
                next=end;
            }else {
                next=baseEvents[i+1];
            }
            now.completableFuture.thenAccept(new Consumer() {
                @Override
                public void accept(Object o) {
                    System.out.println("accept "+o);
                    if(o.equals("fail")){
                        exeBakEvents(backEvent);
                    }else {
                        eventBus.post(next);
                        backEvent.add(new BaseEvent("bak"+o,new CompletableFuture()));
                    }
                }
            });
        }

        eventBus.post(baseEvents[0]);
        return end.completableFuture.get();
    }

    Object exeBakEvents(List<BaseEvent> baseEvents) {
        EventBus eventBus = new EventBus();
        eventBus.register(new BaseEventHandler());

        BaseEvent end = new BaseEvent("end",new CompletableFuture());

        for (int i = baseEvents.size()-1; i >= 0; i--) {
            BaseEvent now = baseEvents.get(i);
            BaseEvent next;
            if(i==0){
                next=end;
            }else {
                next=baseEvents.get(i-1);
            }
            now.completableFuture.thenAccept(new Consumer() {
                @Override
                public void accept(Object o) {
                    eventBus.post(next);
                }
            });
        }

        eventBus.post(baseEvents.get(baseEvents.size()-1));
        try {
            return end.completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    class BaseEvent {
        private String params;
        private CompletableFuture completableFuture;

        public BaseEvent(String params, CompletableFuture completableFuture) {
            this.params = params;
            this.completableFuture = completableFuture;
        }
    }

    class BaseEventHandler {
        @Subscribe
        public void baseHandle(BaseEvent baseEvent) {
            System.out.println("handler "+baseEvent.params);
            if(baseEvent.params.equals("2")){
                baseEvent.completableFuture.complete("fail");
            }else {
                baseEvent.completableFuture.complete(baseEvent.params);
            }

        }
    }
}
