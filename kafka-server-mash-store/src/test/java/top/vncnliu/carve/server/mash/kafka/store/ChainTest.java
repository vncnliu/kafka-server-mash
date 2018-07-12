package top.vncnliu.carve.server.mash.kafka.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.jupiter.api.Test;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: liuyq
 * Date: 2018/7/9
 * Description:
 */
public class ChainTest {

    @Test
    void main() {
        try {
            ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
            Callable<String> callable1 = () -> {
                System.out.println(1);
                return "sss1";
            };
            Callable<String> callable2 = () -> {
                System.out.println(2);
                return "sss2";
            };
            Callable<String> callable3 = () -> {
                System.out.println(3);
                return "sss3";
            };

            Runnable runnable2 = new Runnable() {
                @Override
                public void run() {
                    service.submit(callable3);
                }
            };
            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    ListenableFuture future = service.submit(callable2);
                    future.addListener(runnable2,service);
                }
            };

            ListenableFuture<String> listenableFuture1 = service.submit(callable1);

            listenableFuture1.addListener(runnable1,service);


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    ListenableFuture exe(ListeningExecutorService service, Callable... callables) throws ExecutionException, InterruptedException {
        CompletableFuture<ListenableFuture> future  = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        });
        new Thread(new TestRun(callables,service,new AtomicInteger(0),future)).start();
        return future.get();
    }

    @Test
    void testExe() throws InterruptedException {
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        Callable<String> callable1 = () -> {
            System.out.println(1);
            return "sss1";
        };
        Callable<String> callable2 = () -> {
            if(true){
                throw new IllegalArgumentException();
            }
            System.out.println(2);
            return "sss2";
        };
        Callable<String> callable3 = () -> {
            System.out.println(3);
            return "sss3";
        };
        try {
            ListenableFuture lastFuture = exe(service,callable1,callable2,callable3);
            System.out.println(lastFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Thread.sleep(1000);
    }

    class TestRun implements Runnable {

        private Callable[] callables;
        private List<ListenableFuture> futures = new ArrayList<>();
        private ListeningExecutorService executors;
        private AtomicInteger num;
        private CompletableFuture<ListenableFuture> lastFuture;

        public TestRun(Callable[] callables, ListeningExecutorService executors, AtomicInteger num, CompletableFuture<ListenableFuture> lastFuture) {
            this.callables = callables;
            this.executors = executors;
            this.num = num;
            this.lastFuture = lastFuture;
        }

        @Override
        public void run() {
            int now = num.get();
            if(now<callables.length){
                if(now!=0){
                    try {
                        Object result = futures.get(now-1).get();
                        System.out.println(result);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        lastFuture.complete(futures.get(futures.size()-1));
                        return;
                    }
                }
                ListenableFuture future = executors.submit(callables[now]);
                num.incrementAndGet();
                future.addListener(this,executors);
                futures.add(future);
            }else {
                lastFuture.complete(futures.get(futures.size()-1));
            }
        }
    }
}
