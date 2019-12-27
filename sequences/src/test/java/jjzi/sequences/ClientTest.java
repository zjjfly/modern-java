package jjzi.sequences;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import jjzi.messages.CreateSequences;
import jjzi.messages.DropSequence;
import jjzi.messages.NextValue;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author z00405ze
 */
public class ClientTest {

    private static ActorSystem system;

    private static ActorRef server;

    @BeforeClass
    public static void setup() throws InterruptedException {
        system = ActorSystem.create("test");
        server = system.actorOf(SequencesActor.props(), "sequences");
        CreateSequences message = new CreateSequences();
        message.setName("test");
        server.tell(message, server);
        Thread.sleep(1000);
    }

    @AfterClass
    public static void teardown() throws InterruptedException {
        DropSequence message = new DropSequence();
        message.setName("test");
        server.tell(message, server);
        Thread.sleep(1000);
        TestKit.shutdownActorSystem(system);
        server = null;
        system = null;
    }

    @Test
    public void testNextValue() {
        new TestKit(system) {{
            AtomicLong lastId = new AtomicLong();
            ActorSelection server = system.actorSelection("akka://test/user/sequences");
            NextValue message = new NextValue();
            message.setName("test");
            server.tell(message, getRef());
            awaitCond(this::msgAvailable);
            expectMsgPF("init test data", msg -> {
                lastId.set((Long) msg);
                return msg;
            });

            within(Duration.ofSeconds(5), () -> {
                for (int i = 0; i < 10; i++) {
                    server.tell(message, getRef());
                    awaitCond(this::msgAvailable);
                    expectMsgPF("expect get next and next values", msg -> {
                        Assert.assertEquals(lastId.get()+1, msg);
                        lastId.set((Long)msg);
                        return msg;
                    });
                }
                return null;
            });
        }};

    }

    @Test
    public void testNextValueFromRemote() {
        new TestKit(system) {{
            AtomicLong lastId = new AtomicLong();
            ActorSelection server = system.actorSelection("akka.tcp://test@127.0.0.1:2552/user/sequences");
            NextValue message = new NextValue();
            message.setName("test");
            server.tell(message, getRef());
            awaitCond(this::msgAvailable);
            expectMsgPF("init test data", msg -> {
                lastId.set((Long) msg);
                return msg;
            });

            within(Duration.ofSeconds(5), () -> {
                for (int i = 0; i < 10; i++) {
                    server.tell(message, getRef());
                    awaitCond(this::msgAvailable);
                    expectMsgPF("expect get next and next values", msg -> {
                        Assert.assertEquals(lastId.get()+1, msg);
                        lastId.set((Long)msg);
                        return msg;
                    });
                }
                return null;
            });
        }};
    }

}
