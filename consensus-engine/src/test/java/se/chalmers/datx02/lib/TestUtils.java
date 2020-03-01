package se.chalmers.datx02.lib;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import sawtooth.sdk.protobuf.Message;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static class TestCommsHelper implements AutoCloseable {
        private byte[] connectionId;
        private ZContext ctx;

        private ZMQ.Socket socket;

        private final String DEFAULT_URL = "tcp://*:5671";

        @Override
        public void close() throws Exception {
            socket.close();
            ctx.close();
        }

        public void start(String url) {
            ctx = new ZContext();
            socket = ctx.createSocket(SocketType.ROUTER);
            socket.bind(url);
        }

        public void start() {
            start(DEFAULT_URL);
        }

        public ZMQ.Socket getSocket() {
            return socket;
        }

        public <T> T recvResp(byte[] response, Message.MessageType responseType, Parser<T> requestParser) {
            this.connectionId = socket.recv();
            byte[] data = socket.recv();
            Message requestMsg = null;
            T request;
            try {
                requestMsg = Message.parseFrom(data);

                request = requestParser.parseFrom(requestMsg.getContent());
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }

            Message responseMsg = Message.newBuilder()
                    .setContent(ByteString.copyFrom(response))
                    .setMessageType(responseType)
                    .setCorrelationId(requestMsg.getCorrelationId())
                    .build();
            socket.sendMore(this.connectionId);
            socket.send(responseMsg.toByteArray());

            return request;
        }


        public byte[] sendReqResp(byte[] request, Message.MessageType requestType) {
            Message requestMsg = Message.newBuilder()
                    .setContent(ByteString.copyFrom(request))
                    .setCorrelationId(UUID.randomUUID().toString())
                    .setMessageType(requestType)
                    .build();

            socket.sendMore(this.connectionId);
            socket.send(requestMsg.toByteArray());

            socket.recv(); // the id
            byte[] replyData = socket.recv();
            Message replyMsg = null;
            try {
                replyMsg = Message.parseFrom(replyData);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
            assertEquals(replyMsg.getMessageType(), Message.MessageType.CONSENSUS_NOTIFY_ACK);
            return replyMsg.getContent().toByteArray();
        }

    }
}
