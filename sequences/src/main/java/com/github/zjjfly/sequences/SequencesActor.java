package com.github.zjjfly.sequences;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import clojure.lang.IFn;
import com.github.zjjfly.messages.CreateSequence;
import com.github.zjjfly.messages.DropSequence;
import com.github.zjjfly.messages.ListSequences;
import com.github.zjjfly.messages.NextValue;
import jaskell.util.CR;

/**
 * @author z00405ze
 */
public class SequencesActor extends AbstractActor {

    private final static String seqNameSpace = "com.github.zjjfly.sequences.seq";

    private static IFn creator;

    private static IFn nextVal;

    private static IFn listSeq;

    private static IFn dropSeq;

    static {
        CR.require(seqNameSpace);
        creator = CR.var(seqNameSpace, "create-seq").fn();
        nextVal = CR.var(seqNameSpace, "next-val").fn();
        listSeq = CR.var(seqNameSpace, "list-seq").fn();
        dropSeq = CR.var(seqNameSpace, "drop-seq").fn();
    }

    private long orderId = 0;

    public static Props props() {
        return Props.create(SequencesActor.class, SequencesActor::new);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(CreateSequence.class, msg -> {
            sender().tell(creator.invoke(msg.getName()), self());
        }).match(NextValue.class, msg -> {
            sender().tell(nextVal.invoke(msg.getName()), self());
        }).match(ListSequences.class, msg -> {
            sender().tell(listSeq.invoke(), self());
        }).match(DropSequence.class, msg -> {
            sender().tell(dropSeq.invoke(msg.getName()), self());
        }).build();
    }
}
