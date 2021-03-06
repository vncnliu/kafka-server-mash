package top.vncnliu.carve.server.mash.kafka.store.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * User: liuyq
 * Date: 2018/7/12
 * Description:
 */

public class PrinterA extends AbstractActor {
    static public Props props() {
        return Props.create(PrinterA.class, () -> new PrinterA());
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public PrinterA() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    //System.out.println(getSender().toString());
                    log.info("a receive : {}"+msg);
                    //getSender().tell("return from a",getSelf());
                })
                .build();
    }
}