package se.chalmers.datx02.devmode;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.Engine;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.models.PeerMessage;
import se.chalmers.datx02.lib.models.StartupState;
import se.chalmers.datx02.lib.models.DriverUpdate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DevmodeEngine implements Engine {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicBoolean exit = new AtomicBoolean(false);
    private BlockingQueue<DriverUpdate> updates;
    private StartupState startupState;
    private DevmodeService service;

    @Override
    public void start(BlockingQueue<DriverUpdate> updates, Service service, StartupState startupState) {
        this.service = new DevmodeService(service);
        this.startupState = startupState;
        this.updates = updates;
        try {
            engineLoop();
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Could not parse protocol buffer", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Engine update interrupted", e);
        }
    }

    private void engineLoop() throws InvalidProtocolBufferException, InterruptedException {
        ConsensusBlock chainHead = startupState.getChainHead();
        int wait_time = service.calculateWaitTime(chainHead.getBlockId().toByteArray());

        boolean published_at_height = false;
        Instant start = Instant.now(); // Get time

        service.initializeBlock();

        /*
         * 1. Wait for an incoming message
         * 2. Check for exit
         * 3. Handle the message
         * 4. Check for publishing
         */
        DriverUpdate update;
        ConsensusBlock block;
        while (!exit.get()) {
            update = updates.poll(10, TimeUnit.MILLISECONDS);
            // handle message if existing
            if (update != null) {
                logger.info("Received message: " + update.getMessageType());
                switch (update.getMessageType()) {
                    case CONSENSUS_NOTIFY_ENGINE_DEACTIVATED:
                        this.stop();
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_NEW:
                        logger.debug("Checking consensus data: ");
                        block = ConsensusBlock.parseFrom((byte[]) update.getData());
                        if (Arrays.equals(block.getBlockId().toByteArray(), DevmodeService.NULL_BLOCK_IDENTIFIER)) {
                            logger.warn("WARNING: Received genesis block; ignoring");
                            continue;
                        }
                        if (checkConsensus(block)) {
                            logger.info("Passed consensus check: " + block.getBlockId());
                            service.checkBlock(block.getBlockId().toByteArray());
                        } else {
                            logger.error("Failed consensus check: " + block.getBlockId());
                            service.failBlock(block.getBlockId().toByteArray());
                        }
                        break;
                    case CONSENSUS_NOTIFY_BLOCK_VALID:
                        byte[] blockId = (byte[]) update.getData();
                        block = service.getBlock(blockId);
                        service.sendBlockReceived(block);
                        chainHead = service.getChainHead();

                        long blockNum = block.getBlockNum();
                        long chainHeadBlockNum = chainHead.getBlockNum();
                        String blockIdString = Util.bytesToHex(block.getBlockId().toByteArray());
                        String chainHeadBlockId = Util.bytesToHex(chainHead.getBlockId().toByteArray());

                        logger.info("Choosing between chain heads: \nCurrent: " + chainHeadBlockId + "\nNew: " + blockIdString);
                        if (blockNum > chainHeadBlockNum || (
                                blockNum == chainHeadBlockNum &&
                                        blockIdString.compareTo(chainHeadBlockId) > 0)
                        ) {
                            logger.info("Committing: " + block.getBlockId());
                            service.commitBlock(block.getBlockId().toByteArray());
                        } else if (block.getBlockNum() < chainHead.getBlockNum()) {
                            ConsensusBlock chainBlock = chainHead;
                            // loop backwards till the block has been found
//                            while (!Arrays.equals(chainBlock.getBlockId().toByteArray(), block.getBlockId().toByteArray())) {
                            while(chainBlock.getBlockNum() != block.getBlockNum()) {
                                ByteString previousId = chainBlock.getPreviousId();
                                chainBlock = service.getBlock(previousId.toByteArray());
                                if (chainBlock == null) {
                                    throw new RuntimeException(
                                            String.format(
                                                    "Previous block with id: %s was not found when looping through the chain",
                                                    Util.bytesToHex(previousId.toByteArray()))
                                    );
                                }
                            }
                            String chainBlockId = Util.bytesToHex(chainBlock.getBlockId().toByteArray());
                            if (blockIdString.compareTo(chainBlockId) > 0) {
                                logger.info("Switching to new fork: " + block.getBlockId());
                                service.commitBlock(block.getBlockId().toByteArray());
                            } else {
                                logger.info("Ignoring fork: " + block.getBlockId());
                                service.ignoreBlock(block.getBlockId().toByteArray());
                            }

                        } else {
                            logger.info("Ignoring: " + block.getBlockId());
                            service.ignoreBlock(block.getBlockId().toByteArray());
                        }
                        break;
                    // The chain head was updated, so abandon the
                    // block in progress and start a new one.
                    case CONSENSUS_NOTIFY_BLOCK_COMMIT:
                        ByteString newChainHeadId = ByteString.copyFrom((byte[]) update.getData());

                        logger.info("Chain head updated to " + newChainHeadId + ", abandoning block in progress");
                        service.cancelBlock();
                        wait_time = service.calculateWaitTime(newChainHeadId.toByteArray());
                        published_at_height = false;
                        start = Instant.now();
                        service.initializeBlock();
                        break;
                    case CONSENSUS_NOTIFY_PEER_MESSAGE:
                        PeerMessage peerMessage = (PeerMessage) update.getData();
                        switch (DevmodeMessage.valueOf(peerMessage.getHeader().getMessageType())) {
                            case PUBLISHED:
                                logger.info(
                                        "Received block published message from {}: {}",
                                        Arrays.toString(peerMessage.getSenderId()),
                                        Arrays.toString(peerMessage.getContent())
                                );
                            case RECEIVED:
                                logger.info(
                                        "Received block received message from {}: {}",
                                        Arrays.toString(peerMessage.getSenderId()),
                                        Arrays.toString(peerMessage.getContent()));
                                service.sendBlockAck(peerMessage.getSenderId(), peerMessage.getContent());
                                break;
                            case ACK:
                                logger.info(
                                        "Received block ack from {}: {}",
                                        Arrays.toString(peerMessage.getSenderId()),
                                        Arrays.toString(peerMessage.getContent())
                                );
                                break;
                        }
                        break;
                    default:
                        // Ignore all other message types
                        break;
                }
            }

            // Publish block when timer expires
            if (!published_at_height && Duration.between(Instant.now(), start).abs().getSeconds() > wait_time) {
                logger.info("Timer expired - publishing block");
                byte[] new_blockId = service.finalizeBlock();
                published_at_height = true;

                service.broadcastPublishedBlock(new_blockId);
            }
        }

    }


    public static boolean checkConsensus(ConsensusBlock block) {
        return Arrays.equals(block.getPayload().toByteArray(), createConsensus(block.getSummary().toByteArray()));
    }

    public static byte[] createConsensus(byte[] blockSummary) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write("Devmode".getBytes());
            baos.write(blockSummary);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Could not write bytearrayoutputstream");
        }
    }

    @Override
    public void stop() {
        this.exit.set(true);
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String getName() {
        return "Devmode-java";
    }

    @Override
    public Map<String, String> additionalProtocol() {
        return Collections.emptyMap();
    }
}
