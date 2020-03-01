package se.chalmers.datx02.lib.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import org.junit.jupiter.api.*;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.Communicator;
import se.chalmers.datx02.lib.TestUtils;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.UUID;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ZmqCommunicatorTest {
    private TestUtils.TestCommsHelper commsHelper;

    @BeforeEach
    void before() {
        commsHelper = new TestUtils.TestCommsHelper();
        commsHelper.start();
    }

    @Test()
    @Timeout(value = 2)
    void testSend() throws InvalidProtocolBufferException {
        // prepare the data
        ConsensusRegisterRequest registerRequest = ConsensusRegisterRequest.newBuilder()
                .setName("Mock engine")
                .setVersion("0.1")
                .build();
        ByteString toSend = registerRequest.toByteString();

        new Thread(() -> {
            Communicator c = new ZmqCommunicator("tcp://localhost:5671");
            c.waitForReady();
            ConsensusFuture future = c.send(toSend.toByteArray(), Message.MessageType.CONSENSUS_REGISTER_REQUEST);
        }).start();
        byte[] identity = commsHelper.getSocket().recv();
        commsHelper.getSocket().sendMore(identity);
        byte[] data = commsHelper.getSocket().recv(0);
        Message msg = Message.parseFrom(data);
        ConsensusRegisterRequest response = ConsensusRegisterRequest.parseFrom(msg.getContent());
        ByteString received = response.toByteString();
        assertEquals(toSend, received);
    }

   @Test()
   @Timeout(value = 2)
   void testSendReceive() throws Exception {
        String url = "tcp://localhost:5671";
        final String engineName = "Mock engine";
        final String version = "0.1";
        new Thread(() -> {
            Communicator c = new ZmqCommunicator(url);
            c.waitForReady();
            ConsensusRegisterRequest registerRequest = ConsensusRegisterRequest.newBuilder()
                    .setName(engineName)
                    .setVersion(version)
                    .build();
            ByteString toSend = registerRequest.toByteString();
            ConsensusFuture send = c.send(toSend.toByteArray(), Message.MessageType.CONSENSUS_REGISTER_REQUEST);
            byte[] content = send.result().getContent();

            try {
                ConsensusRegisterResponse registerResponse = ConsensusRegisterResponse.parseFrom(
                       content
                );
                assertEquals(ConsensusRegisterResponse.Status.OK, registerResponse.getStatus());
                Future<Message> messageFuture = c.receive();
                Message message = messageFuture.get();
                c.sendBack(ConsensusNotifyAck.newBuilder().build().toByteArray(), message.getCorrelationId(), Message.MessageType.CONSENSUS_NOTIFY_ACK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

       ConsensusRegisterResponse registerResponse = ConsensusRegisterResponse.newBuilder()
               .setStatus(ConsensusRegisterResponse.Status.OK)
               .build();
       ConsensusRegisterRequest registerRequest = commsHelper.recvResp(registerResponse.toByteArray(), Message.MessageType.CONSENSUS_REGISTER_RESPONSE, ConsensusRegisterRequest.parser());

       assertEquals(engineName, registerRequest.getName());
       assertEquals(version, registerRequest.getVersion());

       // test send
       commsHelper.sendReqResp(PingRequest.newBuilder().build().toByteArray(), Message.MessageType.PING_REQUEST);

   }



    @AfterEach
    void after() throws Exception {
        commsHelper.close();
    }


}