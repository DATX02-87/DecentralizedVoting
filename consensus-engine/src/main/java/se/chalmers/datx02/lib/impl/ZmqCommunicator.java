package se.chalmers.datx02.lib.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.Communicator;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;

public class ZmqCommunicator implements Communicator {
    private final String url;
    private final byte[] zmqId;
    private final ConcurrentMap<String, ConsensusFuture> futures;

    private final BlockingQueue<Message> sendQueue;

    private final BlockingQueue<Message> receiveQueue;

    private final SocketRunnable socketRunnable;

    private final Thread socketThread;

    public ZmqCommunicator(String url) {
        this.url = url;
        this.futures = new ConcurrentHashMap<>();
        this.zmqId = Util.generateId();
        this.sendQueue = new LinkedBlockingQueue<>();
        this.receiveQueue = new LinkedBlockingQueue<>();
        this.socketRunnable = new SocketRunnable(
                sendQueue, receiveQueue, futures, zmqId, url
        );
        this.socketThread = new Thread(socketRunnable);
        this.socketThread.start();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public byte[] getZmqId() {
        return this.zmqId;
    }

    @Override
    public ConsensusFuture send(byte[] content, Message.MessageType messageType) {
        if (this.socketRunnable.getState() != SocketRunnableState.RUNNING) {
            throw new RuntimeException("The socket is not currently running");
        }
        UUID correlationId = UUID.randomUUID();
        String correlationIdString = correlationId.toString();
        Message message = Message.newBuilder()
                .setMessageType(messageType)
                .setCorrelationId(correlationIdString)
                .setContent(ByteString.copyFrom(content))
                .build();
        ConsensusFuture consensusFuture = new ConsensusFuture(messageType);
        this.futures.put(correlationIdString, consensusFuture);

        this.sendQueue.add(message);
        return consensusFuture;
    }

    @Override
    public void sendBack(byte[] content, String correlationId, Message.MessageType messageType) {
        if (this.socketRunnable.getState() != SocketRunnableState.RUNNING) {
            throw new RuntimeException("The socket is not currently running");
        }
        Message message = Message.newBuilder()
                .setMessageType(messageType)
                .setContent(ByteString.copyFrom(content))
                .setCorrelationId(correlationId)
                .build();

        sendQueue.add(message);

    }

    @Override
    public Future<Message> receive() {
        return new FutureTask<>(receiveQueue::take);
    }

    @Override
    public void waitForReady() {
        while(this.socketRunnable.getState() != SocketRunnableState.RUNNING);
    }

    @Override
    public boolean isReady() {
        return socketRunnable.getState() == SocketRunnableState.RUNNING;
    }

    @Override
    public void close() {
        this.socketThread.interrupt();
    }

    private enum SocketRunnableState {
        NOT_STARTED, RUNNING, STOPPING_NOTIFIED, STOPPED
    }

    private static class SocketRunnable implements Runnable, AutoCloseable {
        private final Queue<Message> sendQueue;
        private final Queue<Message> receiveQueue;
        private final byte[] identity;
        private final String addr;
        private final ConcurrentMap<String, ConsensusFuture> futures;
        private ZContext ctx;
        private ZMQ.Socket socket;
        private volatile SocketRunnableState state = SocketRunnableState.NOT_STARTED;

        private SocketRunnable(Queue<Message> sendQueue, Queue<Message> receiveQueue, ConcurrentMap<String, ConsensusFuture> futures, byte[] identity, String addr) {
            this.sendQueue = sendQueue;
            this.identity = identity;
            this.addr = addr;
            this.receiveQueue = receiveQueue;
            this.futures = futures;
        }

        private SocketRunnableState getState() {
            return state;
        }

        @Override
        public void run() {
            try {
                ctx = new ZContext();
                socket = ctx.createSocket(SocketType.DEALER);
                socket.setIdentity(identity);
                socket.connect(addr);
                System.out.println(String.format("ZMQ socket listening on %s", addr));
                state = SocketRunnableState.RUNNING;
                eventLoop();
            } finally {
                state = SocketRunnableState.STOPPING_NOTIFIED;
                close();
                state = SocketRunnableState.STOPPED;
            }
        }

        private void eventLoop() {
            while (!Thread.currentThread().isInterrupted()) {
                pollSend();
                pollReceive();
            }
        }

        private void pollReceive() {
            byte[] recv = socket.recv(ZMQ.DONTWAIT);
            if (recv == null) {
                return;
            }
            System.out.println("Received message");
            try {
                Message received = Message.parseFrom(recv);
                ConsensusFuture consensusFuture = futures.get(received.getCorrelationId());
                if(consensusFuture == null) {
                    consensusFuture.setResponse(received.getContent().toByteArray(), received.getMessageType());
                    futures.remove(received.getCorrelationId());
                } else {
                    receiveQueue.add(received);
                }

            } catch (InvalidProtocolBufferException e) {
                System.out.println("Parsing message failed");
                e.printStackTrace();
            }
        }

        private void pollSend() {
            Message msg = sendQueue.poll();
            if(msg == null) {
                return;
            }
            System.out.println("Sending message");
            socket.send(msg.toByteArray(), 0);
            System.out.println("Message sent");
        }

        @Override
        public void close() {
            System.out.println("SocketRunnable closing down");
            if (socket != null) {
                socket.close();
            }
            if (ctx != null) {
                ctx.close();
            }
        }
    }
}
