package top.vncnliu.carve.server.mash.kafka.store.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;

/**
 * User: liuyq
 * Date: 2018/7/20
 * Description:
 */
public class CommonTest {

    @Test
    void testAsk() throws ExecutionException, InterruptedException {
        final ActorSystem system = ActorSystem.create("helloakka");
        final ActorRef printerActor =
                system.actorOf(Printer.props(), "printerActor");
        CompletableFuture<Object> future1 =
                ask(printerActor, new TestMessage(), 1000).toCompletableFuture();
        System.out.println(future1.get());
    }
}
