package top.vncnliu.carve.server.mash.kafka.store;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.Test;

/**
 * User: liuyq
 * Date: 2018/7/9
 * Description:
 */
public class VertxTest {

    @Test
    void testFuture() throws InterruptedException {
        Future<String> f3 = Future.future();
        f3.setHandler(h -> {
            if(h.succeeded()){
                System.out.println("f3");
            }else {
            }
        });
        Future<String> f2 = Future.future();
        f2.setHandler(h -> {
            if(h.succeeded()){
                System.out.println("f2");
                if(true){
                    throw new IllegalArgumentException();
                }
                f3.complete();
            }else {
            }
        });
        Future<String> f1 = Future.future();
        f1.setHandler(h -> {
            if(h.succeeded()){
                System.out.println("f1");
                f2.complete();
            }else {
            }
        });

        Future<String> future = Future.future();
        future.setHandler(h -> {
            if(h.succeeded()){
                System.out.println("begin");
                f1.complete();
            }else {
            }
        });

        future.complete();

        Vertx vertx = Vertx.vertx();
        vertx.eventBus().send("aa","sss",reply -> {
            if(reply.succeeded()){
                vertx.eventBus().send("aa","sss",reply2 -> {
                    if(reply2.succeeded()){

                    }else {

                    }
                });
            }
        });

    }
}
