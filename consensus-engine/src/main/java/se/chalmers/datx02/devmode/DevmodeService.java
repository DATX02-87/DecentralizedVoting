package se.chalmers.datx02.devmode;

import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.ConsensusBlock;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.exceptions.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DevmodeService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private final StateLog stateLog = new StateLog();

    private final int DEFAULT_WAIT_TIME = 0;
    public final static byte[] NULL_BLOCK_IDENTIFIER = {0, 0, 0, 0, 0, 0, 0, 0};

    private Service service;

    public DevmodeService(Service service){
        this.service = service;
    }

    public ConsensusBlock getChainHead(){
        logger.info("Getting chain head");

        try{
            return this.service.getChainHead();
        } catch (ReceiveErrorException | NoChainHeadException e) {
            logger.warn(e.getLocalizedMessage());
            return null;
        }
    }

    public ConsensusBlock getBlock(byte[] blockId){
        logger.info("Getting block " + Util.bytesToHex(blockId));

        // Generate a list of only one block id
        ArrayList<byte[]> blockList = new ArrayList<>();
        blockList.add(blockId);

        try{
            Map<ByteString, ConsensusBlock> result = this.service.getBlocks(blockList);

            ConsensusBlock resultBlock = (ConsensusBlock) result.values().toArray()[0];

            return resultBlock;
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to get block: " + Util.bytesToHex(blockId));

            return null;
        }
    }

    public void initializeBlock(){
        logger.info("Initializing block");
        try {
            this.service.initializeBlock(null);
            stateLog.setState(DevmodeState.INITIALIZED);
        }
        catch(InvalidStateException | UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to initialize");
            throw new RuntimeException(e);
        }
    }

    public byte[] finalizeBlock(){
        logger.info("Finalizing block");

        // summarize the block
        byte[] summary = null;
        try {
            summary = service.summarizeBlock();
        } catch (InvalidStateException | ReceiveErrorException e) {
            throw new RuntimeException(e);
        } catch (BlockNotReadyException e) {
            logger.debug("Block not ready to summarize");
        }

        while(summary == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                summary = service.summarizeBlock();
            } catch (InvalidStateException | ReceiveErrorException e) {
                throw new RuntimeException(e);
            } catch (BlockNotReadyException e) {
                logger.debug("Block not ready to summarize");
            }
        }

        // create consensus
        byte[] consensus = DevmodeEngine.createConsensus(summary);

        // finalize the block
        byte[] blockId = null;
        try {
            blockId = service.finalizeBlock(consensus);
        } catch (UnknownBlockException | InvalidStateException | ReceiveErrorException e) {
            throw new RuntimeException(e);
        } catch (BlockNotReadyException e) {
            logger.info("Block not ready when finalizing", e);
        }
        while(blockId == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                blockId = service.finalizeBlock(consensus);
            } catch (UnknownBlockException | InvalidStateException | ReceiveErrorException e) {
                throw new RuntimeException(e);
            } catch (BlockNotReadyException e) {
                logger.info("Block not ready when finalizing", e);
            }
        }

        stateLog.setState(DevmodeState.FINALIZED);
        return blockId;

    }

    public void checkBlock(byte[] blockId){
        logger.info("Checking block " + Util.bytesToHex(blockId));

        // Generate a list of only one block id
        ArrayList<byte[]> blockList = new ArrayList<>();
        blockList.add(blockId);

        try{
            this.service.checkBlocks(blockList);
            stateLog.setState(DevmodeState.CHECKED);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to check block");
        }
    }

    public void failBlock(byte[] blockId){
        logger.info("Failing block " + Util.bytesToHex(blockId));

        try{
            this.service.failBlock(blockId);
            stateLog.setState(DevmodeState.FAILED);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to fail block");
        }
    }

    public void ignoreBlock(byte[] blockId){
        logger.info("Ignoring block " + Util.bytesToHex(blockId));

        try{
            this.service.ignoreBlock(blockId);
            stateLog.setState(DevmodeState.IGNORED);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to ignore block");
        }
    }

    public void commitBlock(byte[] blockId){
        logger.info("Commiting block " + Util.bytesToHex(blockId));

        try{
            this.service.commitBlock(blockId);
            stateLog.setState(DevmodeState.COMMITTED);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to commit block");
        }
    }

    public void cancelBlock(){


        try{
            this.service.cancelBlock();
            logger.info("Cancelled block ");
            stateLog.setState(DevmodeState.CANCELLED);
        }
        catch (InvalidStateException e) {
            // do nothing, this might be expected
        } catch (ReceiveErrorException e) {
            logger.warn("Failed to cancel block: " + e.getLocalizedMessage());
        }
    }

    public void broadcastPublishedBlock(byte[] blockId){
        logger.info("Broadcasting published block " + Util.bytesToHex(blockId));

        try{
            this.service.broadcast(DevmodeMessage.PUBLISHED.name(), blockId);
        }
        catch(RuntimeException e){
            logger.warn("Failed to broadcast published block");
        }
    }

    public void sendBlockReceived(ConsensusBlock block){
        try{
            this.service.sendTo(block.getSignerId().toByteArray(), DevmodeMessage.RECEIVED.name(), block.getBlockId().toByteArray());
        }
        catch(RuntimeException e){
            logger.warn("Failed to send block received");
        }
    }

    public void sendBlockAck(byte[] senderId, byte[] blockId){
        try{
            this.service.sendTo(senderId, DevmodeMessage.ACK.name(), blockId);
        }
        catch(RuntimeException e){
            logger.warn("Failed to send block ack");
        }
    }

    public int calculateWaitTime(byte[] blockId){
        Map<String, String> settings = null;
        List<String> settingsList = new ArrayList<>();
        int wait_time;

        settingsList.add("sawtooth.consensus.min_wait_time");
        settingsList.add("sawtooth.consensus.max_wait_time");

        try {
            settings = this.service.getSettings(blockId, settingsList);

            int min_wait_time = Integer.parseInt(settings.get("sawtooth.consensus.min_wait_time"));
            int max_wait_time = Integer.parseInt(settings.get("sawtooth.consensus.max_wait_time"));

            logger.info("Min: " + min_wait_time + " -- Max: " + max_wait_time);

            if(min_wait_time >= max_wait_time)
                wait_time = DEFAULT_WAIT_TIME;
            else
                wait_time = ThreadLocalRandom.current().nextInt(min_wait_time, max_wait_time + 1);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            wait_time = DEFAULT_WAIT_TIME;
        }

        logger.info("Wait time: " + wait_time);

        return wait_time;
    }

    private enum DevmodeState {
        NONE, INITIALIZED, FINALIZED, CHECKED, FAILED, CANCELLED, COMMITTED, IGNORED
    }

    private static class StateLog {
        private final Queue<DevmodeState> log = new LinkedList<>();

        public DevmodeState getCurrentState() {
            return log.peek();
        }

        public void setState(DevmodeState state) {
            log.add(state);
        }
    }
}
