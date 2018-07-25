package top.vncnliu.carve.server.mash.kafka.store;

import com.google.common.selfeventbus.EventBus;
import com.google.common.selfeventbus.Subscribe;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

/**
 * User: liuyq
 * Date: 2018/7/9
 * Description:
 */
public class ChainTest2 {

    EventBus eventBus = new EventBus();

    @Test
    public void testArrayDeque(){
        ArrayDeque arrayDeque = new ArrayDeque();
        while ((arrayDeque.poll()) != null) {
            System.out.println("ss");
        }
    }

    @Test
    public void testEventBusError() throws InterruptedException {
        eventBus.register(new BaseEventHandler());
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseEvent baseEvent = new BaseEvent("1", new CompletableFuture());
                baseEvent.getCompletableFuture().thenAccept(new Consumer() {
                    @Override
                    public void accept(Object o) {
                        eventBus.post(new BaseEvent("11", new CompletableFuture()));
                        //eventBus.post(new DataEvent());
                        testOther();
                    }
                });
                eventBus.post(baseEvent);
            }
        }).start();

        Thread.sleep(10000000);
    }

    private void testOther() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseEvent baseEvent = new BaseEvent("2", new CompletableFuture());
                baseEvent.getCompletableFuture().thenAccept(new Consumer() {
                    @Override
                    public void accept(Object o) {
                        eventBus.post(new BaseEvent("21", new CompletableFuture()));
                    }
                });
                eventBus.post(baseEvent);
            }
        }).start();
    }

    @Test
    void main() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //exeinone();
                    eventBus.register(new BaseEventHandler());
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

        Thread.sleep(1000000);
    }

    Object exeEvents(BaseEvent[] baseEvents) throws ExecutionException, InterruptedException {

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
                        System.out.println("end exe bak");
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

        System.out.println("begin exe bak");
        BaseEvent end = new BaseEvent("end",new CompletableFuture());

        /*for (int i = baseEvents.size()-1; i >= 0; i--) {
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
        }*/

        eventBus.post(end);
        System.out.println("after post ");
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

    class DataEvent {

    }

    class BaseEventHandler {
        @Subscribe
        public void baseHandle(BaseEvent baseEvent) {
            System.out.println("handler "+baseEvent.params);
            if(baseEvent.params.equals("3")){
                baseEvent.completableFuture.complete("fail");
            }else {
                baseEvent.completableFuture.complete(baseEvent.params);
            }
            System.out.println("comole end");
            //baseEvent.completableFuture.complete(baseEvent.params);

        }
        @Subscribe
        public void baseHandle2(DataEvent baseEvent) {
            System.out.println("handler DataEvent");
        }
    }
}
