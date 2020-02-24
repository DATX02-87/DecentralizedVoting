package se.chalmers.datx02.lib.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.*;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import sawtooth.sdk.protobuf.Consensus;
import sawtooth.sdk.protobuf.ConsensusRegisterRequest;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.Communicator;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ZmqCommunicatorTest {
    ZContext ctx;

    ZMQ.Socket socket;

    String url = "tcp://*:5671";

    @BeforeEach
    void before() {
        ctx = new ZContext();
        socket = ctx.createSocket(SocketType.ROUTER);

        socket.bind(url);
    }

    @Test
    void testComms() throws InvalidProtocolBufferException {

        new Thread(() -> {
            Communicator c = new ZmqCommunicator("tcp://localhost:5671");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsensusRegisterRequest registerRequest = ConsensusRegisterRequest.newBuilder()
                    .setName("Mock engine")
                    .setVersion("0.1")
                    .build();
            ConsensusFuture future = c.send(registerRequest.toByteArray(), Message.MessageType.CONSENSUS_REGISTER_REQUEST);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsensusFuture.ConsensusFutureResponse result = future.result();
            System.out.println(result.getMessageType());

        }).start();
        System.out.println("receiving");

        byte[] identity = socket.recv();
        System.out.println(UUID.nameUUIDFromBytes(identity));
        socket.sendMore(identity);
        byte[] data = socket.recv(0);
        Message msg = Message.parseFrom(data);
        ConsensusRegisterRequest registerRequest = ConsensusRegisterRequest.parseFrom(msg.getContent());
        System.out.println(String.format("Received: %s, %s", registerRequest.getName(), registerRequest.getVersion()));
    }

    @AfterEach
    void after() {
        socket.close();;
        ctx.close();
    }


}