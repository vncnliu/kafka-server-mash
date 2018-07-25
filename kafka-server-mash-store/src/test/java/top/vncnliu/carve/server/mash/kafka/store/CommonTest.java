/*
package top.vncnliu.carve.server.mash.kafka.store;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.TestFuture;

import java.util.concurrent.*;
import java.util.function.BiFunction;

*/
/**
 * User: liuyq
 * Date: 2018/7/23
 * Description:
 *//*

public class CommonTest {

    @TestFuture
    public void testFuture() throws InterruptedException {
        new Thread(() -> {
            try {
                EventBus eventBus = new EventBus();
                eventBus.register(new BaseEventHandler());
                ExeEvent[] exeEvents = {new ExeEvent("1"),new ExeEvent("2"),new ExeEvent("3"),new ExeEvent("4")};
                BakEvent[] bakEvents = {new BakEvent("1"),new BakEvent("2"),new BakEvent("3"),new BakEvent("4")};
                CompletableFuture<String> exeFuture = new CompletableFuture<>();
                CompletableFuture<String> bakFuture = new CompletableFuture<>();
                CompletableFuture<BaseStringEvent> testFuture1 = new CompletableFuture<>();
                CompletableFuture<BaseStringEvent> testFuture2 = new CompletableFuture<>();
                CompletableFuture<BaseStringEvent> testFuture3 = new CompletableFuture<>();
                CompletableFuture<BaseStringEvent> testFuture4 = new CompletableFuture<>();
                BaseStringEvent baseStringEvent1 = new BaseStringEvent(testFuture1,"1");
                BaseStringEvent baseStringEvent2 = new BaseStringEvent(testFuture2,"1");
                BaseStringEvent baseStringEvent3 = new BaseStringEvent(testFuture3,"1");
                BaseStringEvent baseStringEvent4 = new BaseStringEvent(testFuture4,"1");

                testFuture.handle(new BiFunction<AbsMashEvent, Throwable, Object>() {
                    @Override
                    public AbsMashEvent apply(AbsMashEvent s, Throwable throwable) {
                        eventBus.post(s);
                        try {
                            return s.future1.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });

                for (int i = 0; i < exeEvents.length; i++) {
                    testFuture1 = testFuture1.handle(new TestBiFunc2(eventBus,baseStringEvent2,bakEvents[0],bakFuture));
                }

                */
/*for (int i = 0; i < exeEvents.length; i++) {
                    exeFuture = exeFuture.handle(new TestBiFunc2(eventBus, exeEvents[i], bakEvents[i],bakFuture));
                }

                exeFuture.complete("begin");*//*


                */
/*CompletableFuture<String> future2 = future1.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new AbsMashEvent(tmp, o));
                    return tmp;
                });*//*

                */
/*CompletableFuture<String> future3= future2.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new AbsMashEvent(tmp, o));
                    return tmp;
                });*//*

                */
/*CompletableFuture<String> future4= future3.thenCompose(o -> {
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new AbsMashEvent(tmp, o));
                    return tmp;
                });*//*


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

    class AbsMashEvent {
        private CompletableFuture<AbsMashEvent> future1;
        private ExeEvent param;

        public AbsMashEvent(CompletableFuture<AbsMashEvent> future1, ExeEvent param) {
            this.future1 = future1;
            this.param = param;
        }

        public CompletableFuture<AbsMashEvent> getFuture1() {
            return future1;
        }

        public void setFuture1(CompletableFuture<AbsMashEvent> future1) {
            this.future1 = future1;
        }

        public ExeEvent getParam() {
            return param;
        }

        public void setParam(ExeEvent param) {
            this.param = param;
        }
    }

    class BaseStringEvent {
        private CompletableFuture<BaseStringEvent> future1;
        private String param;

        public BaseStringEvent(CompletableFuture<BaseStringEvent> future1, String param) {
            this.future1 = future1;
            this.param = param;
        }

        public CompletableFuture<BaseStringEvent> getFuture1() {
            return future1;
        }

        public void setFuture1(CompletableFuture<BaseStringEvent> future1) {
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
        public void test(AbsMashEvent baseEvent){
            System.out.println(JSON.toJSONString(baseEvent));
            */
/*if(baseEvent.getParam().equals("1")||true){
                System.out.println("complete "+baseEvent.getParam());
                baseEvent.getFuture1().complete(""+(Integer.parseInt(baseEvent.getParam())+1));
            }*//*

        }
    }

    */
/*class TestBiFunc implements BiFunction<String, Throwable, String> {

        private EventBus eventBus;

        private ExeEvent exeEvent;

        private BakEvent bakEvent;

        private CompletableFuture<String> bakFuture;

        public TestBiFunc(EventBus eventBus, ExeEvent exeEvent, BakEvent bakEvent, CompletableFuture<String> bakFuture) {
            this.eventBus = eventBus;
            this.exeEvent = exeEvent;
            this.bakEvent = bakEvent;
            this.bakFuture = bakFuture;
        }

        @Override
        public String apply(String s, Throwable throwable) {
            try {
                if(Strings.isNotBlank(s)&&throwable!=null){
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new AbsMashEvent(tmp,exeEvent));
                    return tmp.get();
                } else {
                    eventBus.post(bakEvent);
                    return "";
                }
            } catch (Exception e) {
                return "error";
            }
        }
    }*//*


    class TestBiFunc2 implements BiFunction<BaseStringEvent, Throwable, BaseStringEvent> {

        private EventBus eventBus;

        private BaseStringEvent exeEvent;

        private BakEvent bakEvent;

        private CompletableFuture<String> bakFuture;

        public TestBiFunc2(EventBus eventBus, BaseStringEvent exeEvent, BakEvent bakEvent, CompletableFuture<String> bakFuture) {
            this.eventBus = eventBus;
            this.exeEvent = exeEvent;
            this.bakEvent = bakEvent;
            this.bakFuture = bakFuture;
        }

        @Override
        public BaseStringEvent apply(BaseStringEvent s, Throwable throwable) {
            try {
                eventBus.post(s);
                return s.getFuture1().get();
            } catch (Exception e) {
                return null;
            }
        }
    }

    */
/*class TestBiFuncBak implements BiFunction<String, Throwable, String> {

        private EventBus eventBus;

        private ExeEvent exeEvent;

        private BakEvent bakEvent;

        public TestBiFuncBak(EventBus eventBus, ExeEvent exeEvent, BakEvent bakEvent) {
            this.eventBus = eventBus;
            this.exeEvent = exeEvent;
            this.bakEvent = bakEvent;
        }

        @Override
        public String apply(String s, Throwable throwable) {
            try {
                if(Strings.isNotBlank(s)&&throwable!=null){
                    CompletableFuture<String> tmp = new CompletableFuture<>();
                    eventBus.post(new AbsMashEvent(tmp,exeEvent));
                    return tmp.get();
                }
            } catch (Exception e) {
                return "error";
            }
        }
    }*//*


}
*/
