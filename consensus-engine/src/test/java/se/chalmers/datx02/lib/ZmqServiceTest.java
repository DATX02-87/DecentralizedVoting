package se.chalmers.datx02.lib;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.impl.ZmqService;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ZmqServiceTest {
    private Service service;
    private String url;
    private Communicator communicator;


    @BeforeEach
    void before(@Mock Communicator communicator) {
        this.communicator = communicator;
        service = new ZmqService(communicator, 10);
    }

    private ConsensusFuture makeFuture(Message.MessageType messageType, byte[] content) {
        ConsensusFuture future = new ConsensusFuture(messageType);
        future.setResponse(content, messageType);
        return future;
    }

    @Test
    void testSendTo() {
        ConsensusSendToResponse resp = ConsensusSendToResponse.newBuilder()
                .setStatus(ConsensusSendToResponse.Status.OK)
                .build();
        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_SEND_TO_RESPONSE,
                        resp.toByteArray()));

        service.sendTo(
                "receiverId".getBytes(),
                Message.MessageType.PING_REQUEST,
                "payload".getBytes()
        );

        verify(communicator).send(ConsensusSendToRequest.newBuilder()
                .setContent(ByteString.copyFrom("payload".getBytes()))
                .setReceiverId(ByteString.copyFrom("receiverId".getBytes()))
                .setMessageType(Message.MessageType.PING_REQUEST.toString())
                .build().toByteArray(), Message.MessageType.CONSENSUS_SEND_TO_REQUEST);
    }

    @Test
    void testBroadcast() {
        ConsensusBroadcastResponse resp = ConsensusBroadcastResponse.newBuilder()
                .setStatus((ConsensusBroadcastResponse.Status.OK)).build();
        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_BROADCAST_RESPONSE,
                        resp.toByteArray()));

        service.broadcast(
                Message.MessageType.PING_REQUEST.toString(),
                "payload".getBytes()
        );

        verify(communicator).send(
                ConsensusBroadcastRequest.newBuilder()
                .setContent(ByteString.copyFrom("payload".getBytes()))
                .setMessageType(Message.MessageType.PING_REQUEST.toString())
                .build().toByteArray(), Message.MessageType.CONSENSUS_BROADCAST_REQUEST
        );
    }
}