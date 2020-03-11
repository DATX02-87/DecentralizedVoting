package se.chalmers.datx02.lib;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.exceptions.InvalidState;
import se.chalmers.datx02.lib.exceptions.ReceiveError;
import se.chalmers.datx02.lib.exceptions.UnknownBlock;
import se.chalmers.datx02.lib.impl.ZmqService;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testInitializeBlock() throws ReceiveError, InvalidState, UnknownBlock {
        ConsensusInitializeBlockResponse resp = ConsensusInitializeBlockResponse.newBuilder()
                .setStatus((ConsensusInitializeBlockResponse.Status.OK)).build();
        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_INITIALIZE_BLOCK_RESPONSE,
                        resp.toByteArray()
                ));

        service.initializeBlock(
                "previousId".getBytes()
        );

        verify(communicator).send(
                ConsensusInitializeBlockRequest.newBuilder()
                .setPreviousId(ByteString.copyFrom("previousId".getBytes()))
                .build().toByteArray(), Message.MessageType.CONSENSUS_INITIALIZE_BLOCK_REQUEST
        );
    }

    @Test
    void testFinalizeBlock() throws ReceiveError, InvalidState, UnknownBlock {
        String blockId = "blockId";
        ByteString blockIdBytes = ByteString.copyFromUtf8(blockId);
        ConsensusFinalizeBlockResponse resp = ConsensusFinalizeBlockResponse.newBuilder()
                .setStatus(ConsensusFinalizeBlockResponse.Status.OK)
                .setBlockId(blockIdBytes)
                .build();
        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_FINALIZE_BLOCK_RESPONSE,
                        resp.toByteArray()
                ));

        byte[] response = service.finalizeBlock(
                blockIdBytes.toByteArray()
        );

        verify(communicator).send(
                ConsensusFinalizeBlockRequest.newBuilder()
                .setData(blockIdBytes)
                        .build().toByteArray(), Message.MessageType.CONSENSUS_FINALIZE_BLOCK_REQUEST
        );
        assertArrayEquals(blockIdBytes.toByteArray(), response);
    }

    @Test
    void testCancelBlock() throws ReceiveError, InvalidState {
        ConsensusCancelBlockResponse resp = ConsensusCancelBlockResponse.newBuilder()
                .setStatus(ConsensusCancelBlockResponse.Status.OK).build();

        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_CANCEL_BLOCK_RESPONSE,
                        resp.toByteArray()
                ));

        service.cancelBlock();

        verify(communicator).send(
                ConsensusCancelBlockRequest.newBuilder().build().toByteArray(),
                Message.MessageType.CONSENSUS_CANCEL_BLOCK_REQUEST
        );
    }

    @Test
    void testCheckBlocks() throws UnknownBlock, ReceiveError {
        List<byte[]> priority = new ArrayList<>();
        priority.add("test1".getBytes());
        priority.add("test2".getBytes());

        ConsensusCheckBlocksResponse resp = ConsensusCheckBlocksResponse.newBuilder()
                .setStatus(ConsensusCheckBlocksResponse.Status.OK).build();

        when(communicator.send(any(byte[].class), any(Message.MessageType.class))).thenReturn(
                makeFuture(
                        Message.MessageType.CONSENSUS_CHECK_BLOCKS_RESPONSE,
                        resp.toByteArray()
                ));

        service.checkBlocks(priority);

        verify(communicator).send(
                ConsensusCheckBlocksRequest.newBuilder()
                        .addAllBlockIds(priority.stream()
                                .map(ByteString::copyFrom)
                                .collect(Collectors.toList()))
                        .build().toByteArray(),
                Message.MessageType.CONSENSUS_CHECK_BLOCKS_REQUEST
        );
    }
}