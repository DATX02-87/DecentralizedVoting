package se.chalmers.datx02.lib.impl;

import com.google.protobuf.InvalidProtocolBufferException;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.devmode.DevmodeEngine;
import se.chalmers.datx02.lib.Communicator;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.Engine;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.models.PeerMessage;
import se.chalmers.datx02.lib.models.StartupState;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.models.ConsensusFuture;
import se.chalmers.datx02.lib.models.DriverUpdate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ZmqDriver implements Driver {
    private final static Logger LOGGER = Logger.getLogger(DevmodeEngine.class.getName());
    public static final int REGISTER_TIMEOUT = 300;
    public static final int SERVICE_TIMEOUT = 300;
    private final Engine engine;
    private Communicator communicator;
    private BlockingQueue<DriverUpdate> driverUpdates;
    private final AtomicBoolean exit;
    private Thread driverThread;

    public ZmqDriver(Engine engine) {
        this.engine = engine;
        this.exit = new AtomicBoolean(false);
    }

    @Override
    public void start(String endpoint) {
        this.communicator = new ZmqCommunicator(endpoint);
        StartupState startupState = null;
        try {
            startupState = register();
        } catch (ReceiveErrorException receiveErrorException) {
            receiveErrorException.printStackTrace();
        }
        if (startupState == null) {
            startupState = this.waitUntilActive();
        }

        this.driverUpdates = new LinkedBlockingQueue<>();
        driverThread = new Thread(new DriverThread(this::stop, driverUpdates, communicator, exit));
        driverThread.start();

        Service service = new ZmqService(communicator, SERVICE_TIMEOUT);
        this.engine.start(driverUpdates, service, startupState);
        this.stop();
        try {
            driverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private StartupState waitUntilActive() {
        Future<Message> receiveFuture = communicator.receive();
        while (true) {
            Message msg;
            try {
                msg = receiveFuture.get(1, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            if (msg.getMessageType() == Message.MessageType.CONSENSUS_NOTIFY_ENGINE_ACTIVATED) {
                ConsensusNotifyEngineActivated notification;
                try {
                    notification = ConsensusNotifyEngineActivated.parseFrom(msg.getContent());
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException(e);
                }
                StartupState startupState = new StartupState(notification.getChainHead(), notification.getPeersList(), notification.getLocalPeerInfo());
                LOGGER.info("Received activation message with startup state: " + startupState.toString());
                communicator.sendBack(ConsensusNotifyAck.newBuilder().build().toByteArray(), msg.getCorrelationId(), Message.MessageType.CONSENSUS_NOTIFY_ACK);
                return startupState;
            }
            LOGGER.warning(String.format("Received message of type %s, when we are waiting for activation message", msg.getMessageType()));
            receiveFuture = communicator.receive();
        }
    }

    @Override
    public void stop() {
        this.exit.set(true);
        this.engine.stop();
        if (Thread.currentThread().getId() == driverThread.getId()) {
            // is stopping from the driver thread, do not join with self thread
            return;
        }
        try {
            this.driverThread.join();
            this.communicator.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private StartupState register() throws ReceiveErrorException {
        this.communicator.waitForReady();

        ConsensusRegisterRequest registerRequest = ConsensusRegisterRequest.newBuilder()
                .setName(engine.getName())
                .setVersion(engine.getVersion())
                .build();

        while (true) {
            ConsensusFuture future = communicator.send(registerRequest.toByteArray(), Message.MessageType.CONSENSUS_REGISTER_REQUEST);
            ConsensusRegisterResponse response;
            try {
                response = ConsensusRegisterResponse.parseFrom(future.result().getContent());
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
            if (response.getStatus() == ConsensusRegisterResponse.Status.NOT_READY) {
                continue;
            }
            if (response.getStatus() == ConsensusRegisterResponse.Status.OK) {
                if (response.hasChainHead() && response.hasLocalPeerInfo()) {
                    return new StartupState(response.getChainHead(), response.getPeersList(), response.getLocalPeerInfo());
                }
                return null;
            }
            throw new ReceiveErrorException("Registration failed with status " + response.getStatus());

        }
    }

    private static class DriverThread implements Runnable {
        private final Callback stopCallback;
        private final BlockingQueue<DriverUpdate> updates;
        private final Communicator communicator;
        private final AtomicBoolean exit;

        private DriverThread(Callback stopCallback, BlockingQueue<DriverUpdate> updates, Communicator communicator, AtomicBoolean exit) {
            this.stopCallback = stopCallback;
            this.updates = updates;
            this.communicator = communicator;
            this.exit = exit;
        }

        @Override
        public void run() {
            Future<Message> future = communicator.receive();
            while (!exit.get()) {
                Message message;
                try {
                    message = future.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    // timed out, continue
                    continue;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // update the future
                future = communicator.receive();
                DriverUpdate d = process(message);
                if (d.getMessageType() == Message.MessageType.PING_REQUEST) {
                    continue;
                }
                updates.add(d);
            }
        }

        private DriverUpdate process(Message msg) {
            Message.MessageType msgType = msg.getMessageType();
            Object data = null;
            try {
                switch (msgType) {
                    case CONSENSUS_NOTIFY_PEER_CONNECTED:
                        ConsensusNotifyPeerConnected peerConnected = ConsensusNotifyPeerConnected.parseFrom(msg.getContent());
                        data = peerConnected.getPeerInfo().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_PEER_DISCONNECTED:
                        ConsensusNotifyPeerDisconnected peerDisconnected = ConsensusNotifyPeerDisconnected.parseFrom(msg.getContent());
                        data = peerDisconnected.getPeerId().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_PEER_MESSAGE:
                        ConsensusNotifyPeerMessage notifyPeerMessage = ConsensusNotifyPeerMessage.parseFrom(msg.getContent());
                        ConsensusPeerMessageHeader header = ConsensusPeerMessageHeader.parseFrom(notifyPeerMessage.getMessage().getHeader());
                        ConsensusPeerMessage message = notifyPeerMessage.getMessage();

                        PeerMessage peerMessage = new PeerMessage(
                                header,
                                message.getHeader().toByteArray(),
                                message.getHeaderSignature().toByteArray(),
                                message.getContent().toByteArray(),
                                notifyPeerMessage.getSenderId().toByteArray());

                        data = peerMessage;
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_NEW:
                        ConsensusNotifyBlockNew notifyBlockNew = ConsensusNotifyBlockNew.parseFrom(msg.getContent());
                        data = notifyBlockNew.getBlock().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_VALID:
                        ConsensusNotifyBlockValid notifyBlockValid = ConsensusNotifyBlockValid.parseFrom(msg.getContent());
                        data = notifyBlockValid.getBlockId().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_INVALID:
                        ConsensusNotifyBlockInvalid notifyBlockInvalid = ConsensusNotifyBlockInvalid.parseFrom(msg.getContent());
                        data = notifyBlockInvalid.getBlockId().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_COMMIT:
                        ConsensusNotifyBlockCommit notifyBlockCommit = ConsensusNotifyBlockCommit.parseFrom(msg.getContent());
                        data = notifyBlockCommit.getBlockId().toByteArray();
                        break;
                    case CONSENSUS_NOTIFY_ENGINE_DEACTIVATED:
                        this.stopCallback.callback();
                        break;
                    case PING_REQUEST:
                        return new DriverUpdate(msgType, null);
                    default:
                        throw new ReceiveErrorException("No valid messagetype was sent to the driver");
                }
            } catch (InvalidProtocolBufferException | ReceiveErrorException e) {
                throw new RuntimeException("Message could not be parsed to a protobuf", e);
            }
            // send ack back to the validator
            communicator.sendBack(ConsensusNotifyAck.newBuilder().build().toByteArray(), msg.getCorrelationId(), Message.MessageType.CONSENSUS_NOTIFY_ACK);
            return new DriverUpdate(msgType, data);
        }
    }

    private interface Callback {
        void callback();
    }
}
