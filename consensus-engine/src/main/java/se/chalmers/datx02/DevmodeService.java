package se.chalmers.datx02;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DevmodeService {
    private boolean not_ready_to_summarize,
                    not_ready_to_finalize;

    private final int DEFAULT_WAIT_TIME = 0;
    public final byte[] NULL_BLOCK_IDENTIFIER = {0, 0, 0, 0, 0, 0, 0, 0};

    private Service service;

    public DevmodeService(Service service){
        this.service = service;
    }

    public ConsensusBlock getChainHead(){
        System.out.println("Getting chain head");

        try{
            this.service.getChainHead();
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to get chain head");
        }
    }

    public ConsensusBlock getBlock(byte[] blockId){
        System.out.println("Getting block " + HexBin.encode(blockId));

        // Generate a list of only one block id
        ArrayList<byte[]> blockList = new ArrayList<>();
        blockList.add(blockId);

        try{
            Map<byte[], ConsensusBlock> result = this.service.getBlocks(blockList);

            ConsensusBlock resultBlock = (ConsensusBlock) result.values().toArray()[0];

            return resultBlock;
        }
            catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to get chain head");

            return null;
        }
    }

    public void initializeBlock(){
        System.out.println("Initializing block");
        try {
            this.service.initializeBlock(null);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to initialize");
        }
    }

    public byte[] finalizeBlock(){
        System.out.println("Finalizing block");

        // Try to summarize block
        byte[] summary = null;

        while(true) {
            try{
                summary = this.service.summarizeBlock();
                break;
            }
            catch(RuntimeException e){
                System.out.println("Block not ready to summarize");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // TODO: Finish finalizeBlock
        // TODO: Impl. helper functions

        return null;
    }

    public void checkBlock(byte[] blockId){
        System.out.println("Checking block " + HexBin.encode(blockId));

        // Generate a list of only one block id
        ArrayList<byte[]> blockList = new ArrayList<>();
        blockList.add(blockId);

        try{
            this.service.checkBlocks(blockList);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to check block");
        }
    }

    public void failBlock(byte[] blockId){
        System.out.println("Failing block " + HexBin.encode(blockId));

        try{
            this.service.failBlock(blockId);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to fail block");
        }
    }

    public void ignoreBlock(byte[] blockId){
        System.out.println("Ignoring block " + HexBin.encode(blockId));

        try{
            this.service.ignoreBlock(blockId);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to ignore block");
        }
    }

    public void commitBlock(byte[] blockId){
        System.out.println("Commiting block " + HexBin.encode(blockId));

        try{
            this.service.commitBlock(blockId);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to commit block");
        }
    }

    public void cancelBlock(){
        System.out.println("Canceling block ");

        try{
            this.service.cancelBlock();
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to cancel block");
        }
    }

    public void broadcastPublishedBlock(byte[] blockId){
        System.out.println("Broadcasting published block " + HexBin.encode(blockId));

        try{
            this.service.broadcast("published", blockId);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to broadcast published block");
        }
    }

    public void sendBlockReceived(ConsensusBlock block){
        /*
        * TODO: Check signer_id and block_id, also replace with right messageType
         */
        try{
            this.service.sendTo(block.getSignerId().toByteArray(), Message.MessageType.RESPONSE., block.getBlockId().toByteArray());
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to send block received");
        }
    }

    public void sendBlockAck(byte[] senderId, byte[] blockId){

        // TODO: Change to right messageType
        try{
            this.service.sendTo(senderId, Message.MessageType.CONSENSUS_NOTIFY_ACK, blockId);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to send block ack");
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

            System.out.println("Min: " + min_wait_time + " -- Max: " + max_wait_time);

            if(min_wait_time >= max_wait_time)
                wait_time = DEFAULT_WAIT_TIME;
            else
                wait_time = ThreadLocalRandom.current().nextInt(min_wait_time, max_wait_time + 1);
        }
        catch(RuntimeException e){
            wait_time = DEFAULT_WAIT_TIME;
        }

        System.out.println("Wait time: " + wait_time);

        return wait_time;
    }
}
