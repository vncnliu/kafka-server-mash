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

public class Printer extends AbstractActor {
    static public Props props() {
        return Props.create(Printer.class, () -> new Printer());
    }

    static public class Greeting {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public Printer() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greeting.class, greeting -> {
                    log.info(greeting.message);
                })
                .match(String.class, msg -> {
                    log.info(msg);
                })
                .build();
    }
}