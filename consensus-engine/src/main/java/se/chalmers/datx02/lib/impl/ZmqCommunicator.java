package se.chalmers.datx02.lib.impl;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.concurrent.atomic.AtomicBoolean;


public class ZmqCommunicator implements Communicator {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final String url;
    private final byte[] zmqId;
    private final ConcurrentMap<String, ConsensusFuture> futures;

    private final BlockingQueue<Message> sendQueue;

    private final BlockingQueue<Message> receiveQueue;

    private final SocketRunnable socketRunnable;

    private final Thread socketThread;

    private final AtomicBoolean exit;

    public ZmqCommunicator(String url) {
        this.url = url;
        this.futures = new ConcurrentHashMap<>();
        this.zmqId = Util.generateId();
        this.sendQueue = new LinkedBlockingQueue<>();
        this.receiveQueue = new LinkedBlockingQueue<>();
        exit = new AtomicBoolean(false);
        this.socketRunnable = new SocketRunnable(
                sendQueue, receiveQueue, futures, zmqId, url, exit
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
        return CompletableFuture.supplyAsync(() -> {
            try {
                return receiveQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
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
    public void close() throws InterruptedException {
        this.exit.set(true);
        this.socketThread.join();
    }

    private enum SocketRunnableState {
        NOT_STARTED, RUNNING, STOPPING_NOTIFIED, STOPPED
    }

    private static class SocketRunnable implements Runnable, AutoCloseable {
        final Logger logger = LoggerFactory.getLogger(getClass());
        private final Queue<Message> sendQueue;
        private final Queue<Message> receiveQueue;
        private final byte[] identity;
        private final String addr;
        private final ConcurrentMap<String, ConsensusFuture> futures;
        private final AtomicBoolean exit;
        private ZContext ctx;
        private ZMQ.Socket socket;
        private volatile SocketRunnableState state = SocketRunnableState.NOT_STARTED;

        private SocketRunnable(Queue<Message> sendQueue, Queue<Message> receiveQueue, ConcurrentMap<String, ConsensusFuture> futures, byte[] identity, String addr, AtomicBoolean exit) {
            this.sendQueue = sendQueue;
            this.identity = identity;
            this.addr = addr;
            this.receiveQueue = receiveQueue;
            this.futures = futures;
            this.exit = exit;
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
                logger.info("ZMQ socket listening on {}", addr);
                state = SocketRunnableState.RUNNING;
                eventLoop();
            } finally {
                state = SocketRunnableState.STOPPING_NOTIFIED;
                close();
                state = SocketRunnableState.STOPPED;
            }
        }

        private void eventLoop() {
            while (!exit.get()) {
                pollSend();
                pollReceive();
            }
        }

        private void pollReceive() {
            byte[] recv = socket.recv(ZMQ.DONTWAIT);
            if (recv == null) {
                return;
            }
            logger.debug("Received message");
            try {
                Message received = Message.parseFrom(recv);
                ConsensusFuture consensusFuture = futures.get(received.getCorrelationId());
                if(consensusFuture != null) {
                    consensusFuture.setResponse(received.getContent().toByteArray(), received.getMessageType());
                    futures.remove(received.getCorrelationId());
                } else {
                    receiveQueue.add(received);
                }

            } catch (InvalidProtocolBufferException e) {
                logger.warn("Parsing message failed");
                e.printStackTrace();
            }
        }

        private void pollSend() {
            Message msg = sendQueue.poll();
            if(msg == null) {
                return;
            }
            logger.debug("Sending message");
            socket.send(msg.toByteArray(), 0);
            logger.debug("Message sent");
        }

        @Override
        public void close() {
            logger.info("SocketRunnable closing down");
            if (socket != null) {
                socket.close();
            }
            if (ctx != null) {
                ctx.close();
            }
        }
    }
}
