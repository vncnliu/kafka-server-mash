package top.vncnliu.carve.server.mash.kafka.store.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.japi.Function;
import akka.pattern.Patterns;
import akka.stream.actor.ActorPublisherMessage;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import static akka.pattern.PatternsCS.ask;
import static akka.pattern.PatternsCS.askWithReplyTo;

/**
 * User: liuyq
 * Date: 2018/7/12
 * Description:
 */
public class AkkaQuickstart {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");
        try {
            final ActorRef printerActor =
                    system.actorOf(Printer.props(), "printerActor");
            final ActorRef printerActorA =
                    system.actorOf(PrinterA.props(), "printerActorA");
            final ActorRef printerActorB =
                    system.actorOf(PrinterB.props(), "printerActorB");
            final ActorRef printerActorC =
                    system.actorOf(PrinterC.props(), "printerActorC");


            CompletableFuture<Object> future1 =
                    ask(printerActor, "request", 1000).toCompletableFuture();

            //printerActorB.tell("hello i am b", printerActorA);

            printerActorB.tell("hello i am a, i am wait", printerActorA);

            printerActorC.tell("hello i am b, i am wait",printerActorB);

            /*final ActorRef howdyGreeter =
                    system.actorOf(Greeter.props("Howdy", printerActor), "howdyGreeter");
            final ActorRef helloGreeter =
                    system.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
            final ActorRef goodDayGreeter =
                    system.actorOf(Greeter.props("Good day", printerActor), "goodDayGreeter");

            howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
            howdyGreeter.tell(new Greet(), ActorRef.noSender());

            howdyGreeter.tell(new WhoToGreet("Lightbend"), ActorRef.noSender());
            howdyGreeter.tell(new Greet(), ActorRef.noSender());

            helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());
            helloGreeter.tell(new Greet(), ActorRef.noSender());

            goodDayGreeter.tell(new WhoToGreet("Play"), ActorRef.noSender());
            goodDayGreeter.tell(new Greet(), ActorRef.noSender());

            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();*/
        } catch (Exception ioe) {
        } finally {
            system.terminate();
        }
    }
}
