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

public class PrinterC extends AbstractActor {
    static public Props props() {
        return Props.create(PrinterC.class, () -> new PrinterC());
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public PrinterC() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    //log.info(getSender().toString());
                    log.info("c receive : {}"+msg);
                    if(msg.contains("wait")){
                        getSender().tell("return from c",getSelf());
                    }
                })
                .build();
    }
}