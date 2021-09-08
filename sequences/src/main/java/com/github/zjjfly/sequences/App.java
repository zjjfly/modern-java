package com.github.zjjfly.sequences;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import com.github.zjjfly.messages.NextValue;

/**
 * @author z00405ze
 */
public class App extends AbstractActor {

    private LoggingAdapter log = Logging.getLogger(this.context().system(), this.getClass());

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(Long.class, msg -> {
            log.info("received long: {}", msg);
        }).build();
    }

    public static void main(String[] args) {
        //使用不同的Context System也不会影响结果
        ActorSystem seqSys = ActorSystem.create("sequences");
        ActorSystem appSys = ActorSystem.create("app");
        ActorRef seqRef = seqSys.actorOf(SequencesActor.props(), "sequences");
        ActorRef mineRef = appSys.actorOf(Props.create(App.class), "ask");
        seqRef.tell("next", mineRef);
        //使用多线程并发的获取序列,得到的结果仍然是顺序的,说明Actor的异步环境切换由Context System维护,所以每个Actor内部是并发安全的
        NextValue msg = new NextValue();
        msg.setName("orders");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    seqRef.tell(msg, mineRef);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

    }
}
