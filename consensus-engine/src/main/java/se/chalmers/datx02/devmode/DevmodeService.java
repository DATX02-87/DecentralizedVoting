package se.chalmers.datx02.devmode;

import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.Message.MessageType;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.Util;
import se.chalmers.datx02.lib.exceptions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DevmodeService {
    private boolean not_ready_to_summarize,
            not_ready_to_finalize;

    final Logger logger = LoggerFactory.getLogger(getClass());

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
        }
        catch(RuntimeException e){
            logger.warn("Failed to get chain head");
            return null;
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
        }
        catch(InvalidStateException | UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to initialize");
        }
    }

    public byte[] finalizeBlock(){
        logger.info("Finalizing block");

        // Try to summarize block
        byte[] summary = null;


        while(true) {
            // Log
            if(!not_ready_to_summarize){
                not_ready_to_summarize = true;
                logger.info("Block not ready to summarize");
            }

            // Sleep thread
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Could not sleep thread");
            }

            // Try to get summarized block
            try{
                summary = this.service.summarizeBlock();
                break;
            }
            catch(RuntimeException e){
                logger.warn("Failed to summarize block");
                break;
            } catch (InvalidStateException | BlockNotReadyException | ReceiveErrorException e) {
                logger.info("Could not summarize block. Retrying...");
            }
        }

        not_ready_to_summarize = false;

        byte[] consensus = DevmodeEngine.createConsensus(summary);

        byte[] block_id = new byte[0];
        try {
            block_id = this.service.finalizeBlock(consensus);
        } catch (InvalidStateException | UnknownBlockException | ReceiveErrorException e) {
            e.printStackTrace();
        }

        while(true) {
            // Log
            if(!not_ready_to_finalize){
                not_ready_to_finalize = true;
                logger.info("Block not ready to finalize");
            }

            // Sleep thread
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException("Could not sleep thread");
            }

            // Try to get summarized block
            try{
                block_id = this.service.finalizeBlock(consensus);
                break;
            }
            catch (InvalidStateException | UnknownBlockException | ReceiveErrorException e) {
                logger.warn("Failed to finalize block", e);
                break;
            }
        }
        not_ready_to_finalize = false;

        logger.info("Block has been finalized sucessfully : " + Util.bytesToHex(block_id));

        return block_id;
    }

    public void checkBlock(byte[] blockId){
        logger.info("Checking block " + Util.bytesToHex(blockId));

        // Generate a list of only one block id
        ArrayList<byte[]> blockList = new ArrayList<>();
        blockList.add(blockId);

        try{
            this.service.checkBlocks(blockList);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to check block");
        }
    }

    public void failBlock(byte[] blockId){
        logger.info("Failing block " + Util.bytesToHex(blockId));

        try{
            this.service.failBlock(blockId);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to fail block");
        }
    }

    public void ignoreBlock(byte[] blockId){
        logger.info("Ignoring block " + Util.bytesToHex(blockId));

        try{
            this.service.ignoreBlock(blockId);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to ignore block");
        }
    }

    public void commitBlock(byte[] blockId){
        logger.info("Commiting block " + Util.bytesToHex(blockId));

        try{
            this.service.commitBlock(blockId);
        }
        catch(UnknownBlockException | ReceiveErrorException e){
            logger.warn("Failed to commit block");
        }
    }

    public void cancelBlock(){
        logger.info("Canceling block ");

        try{
            this.service.cancelBlock();
        }
        catch (InvalidStateException | ReceiveErrorException e) {
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
}
