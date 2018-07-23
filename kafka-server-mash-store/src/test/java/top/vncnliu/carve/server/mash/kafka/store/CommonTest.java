package top.vncnliu.carve.server.mash.kafka.store;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * User: liuyq
 * Date: 2018/7/23
 * Description:
 */
public class CommonTest {

    @Test
    public void testSc() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(100);
                //ExecutorService executorService = Executors.newFixedThreadPool(5);
                //for (int i = 0; i < 10; i++) {
                    scheduler.scheduleAtFixedRate(new TestWorker(),0,1,TimeUnit.SECONDS);
                    //executorService.submit(new TestWorker());
                //}
            }
        }).start();
        Thread.sleep(1000000);
    }

    class TestWorker implements Runnable {

        @Override
        public void run() {
            System.out.println("run");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testFuture() throws InterruptedException {
        new Thread(() -> {
            try {
                EventBus eventBus = new EventBus();
                eventBus.register(new BaseEventHandler());
                ExeEvent[] events = {new ExeEvent("1"),new ExeEvent("2"),new ExeEvent("3"),new ExeEvent("4")};
                BakEvent[] bakEvents = {new BakEvent("1"),new BakEvent("2"),new BakEvent("3"),new BakEvent("4")};
                CompletableFuture<String> future = new CompletableFuture<>();

                for (int i = 0; i < events.length; i++) {
                    future = future.handle(new TestBiFunc(eventBus, events[i], bakEvents[i]));
                }

                future.complete("begin");

                /*CompletableFuture<String> future2 = future1.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new BaseEvent(tmp, o));
                    return tmp;
                });*/
                /*CompletableFuture<String> future3= future2.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new BaseEvent(tmp, o));
                    return tmp;
                });*/
                /*CompletableFuture<String> future4= future3.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new BaseEvent(tmp, o));
                    return tmp;
                });*/

                //testCompleFuture(future1);
            } catch (Exception e){
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(5000);
    }

    private void testCompleFuture(CompletableFuture<String> future1) {
        CompletableFuture<String> future2 = future1.thenApply(o -> {
            System.out.println(o);
            return "exe 2";
        });
        CompletableFuture<String> future3 = future2.thenApply(o -> {
            System.out.println(o);
            return "exe 3";
        });
        future3.thenAccept(o -> {
            System.out.println(o);
            System.out.println("end");
        });
        future3.handle(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                System.out.println("bak 3");
                future2.cancel(false);
                return null;
            }
        });
        future2.handle(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                System.out.println("bak 2");
                future1.cancel(false);
                return null;
            }
        });
        future1.handle(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                System.out.println("bak 1");
                return null;
            }
        });
        future1.cancel(false);
        //future1.complete("exe 1");
    }

    class ExeEvent {
        private String param;

        public ExeEvent(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }

    class BakEvent {
        private String param;

        public BakEvent(String param) {
            this.param = param;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }

    class BaseEvent {
        private CompletableFuture<String> future1;
        private String param;

        public BaseEvent(CompletableFuture<String> future1, String param) {
            this.future1 = future1;
            this.param = param;
        }

        public CompletableFuture<String> getFuture1() {
            return future1;
        }

        public void setFuture1(CompletableFuture<String> future1) {
            this.future1 = future1;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }
    }

    class BaseEventHandler {
        @Subscribe
        public void test(BaseEvent baseEvent){
            System.out.println(JSON.toJSONString(baseEvent));
            if(baseEvent.getParam().equals("1")||true){
                System.out.println("complete "+baseEvent.getParam());
                baseEvent.getFuture1().complete(""+(Integer.parseInt(baseEvent.getParam())+1));
            }
        }
    }

    class TestBiFunc implements BiFunction<String, Throwable, String> {

        private EventBus eventBus;

        private ExeEvent exeEvent;

        private BakEvent bakEvent;

        public TestBiFunc(EventBus eventBus, ExeEvent exeEvent, BakEvent bakEvent) {
            this.eventBus = eventBus;
            this.exeEvent = exeEvent;
            this.bakEvent = bakEvent;
        }

        @Override
        public String apply(String s, Throwable throwable) {
            try {
                if(Strings.isNotBlank(s)&&throwable!=null){
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new BaseEvent(tmp,exeEvent));
                    return tmp.get();
                } else {
                    eventBus.post(bakEvent);
                    return
                }
            } catch (Exception e) {
                return "error";
            }
        }
    }

}
