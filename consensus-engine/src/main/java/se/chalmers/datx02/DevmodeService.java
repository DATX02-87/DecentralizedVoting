package se.chalmers.datx02;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import se.chalmers.datx02.lib.ZmqService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevmodeService {
    private boolean not_ready_to_summarize,
                    not_ready_to_finalize;

    private final int DEFAULT_WAIT_TIME = 0;
    private final int[] NULL_BLOCK_IDENTIFIER = {0, 0, 0, 0, 0, 0, 0, 0};

    private ZmqService service;

    public DevmodeService(ZmqService service){
        this.service = service;
    }

    public ConsensusBlock getChainHead(){
        System.out.println("Getting chain head");

        // Try to get Block head
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
        ArrayList<byte[]> blockList = new ArrayList<byte[]>();
        blockList.add(blockId);

        // Try to get block
        try{
            Map<byte[], ConsensusBlock> result = this.service.getBlocks(blockList);
            ConsensusBlock resultBlock = result.values().toArray()[0];

            return resultBlock;
        }
            catch(RuntimeException e){
            e.printStackTrace();
            System.out.println("Failed to get chain head");

            return null;
        }
    }

    public void initializeBlock(){

    }

    public byte[] finalizeBlock(){
        return null;
    }

    public void checkBlock(byte[] blockId){

    }

    public void failBlock(byte[] blockId){

    }

    public void ignoreBlock(byte[] blockId){

    }

    public void commitBlock(byte[] blockId){

    }

    public void cancelBlock(){

    }

    public void broadcastPublishedBlock(byte[] blockId){

    }

    public void sendBlockReceived(byte[] blockId){

    }

    public void sendBlockAck(byte[] senderId, byte[] blockId){

    }

    public int calculateWaitTime(byte[] blockId){

        return 0;
    }
}
