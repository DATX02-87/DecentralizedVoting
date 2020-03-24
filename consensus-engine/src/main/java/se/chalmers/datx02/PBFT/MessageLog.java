package se.chalmers.datx02.PBFT;

import sawtooth.sdk.protobuf.Block;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MessageLog {

    private HashMap<byte[], Block> unvalidated_blocks;

    private HashSet<Block> blocks;
    private HashSet<ParsedMessage> messages;

    private int max_log_size;

    // For display
    public String toString(){
        return null;
    }

    // TODO: Impl.

    public MessageLog(Config config){
        this.unvalidated_blocks = new HashMap<>();
        this.blocks = new HashSet<>();
        this.messages = new HashSet<>();
        this.max_log_size = config.getMaxLogSize();
    }

    public void addValidatedBlock(Block block){}

    public void addUnvalidatedBlock(Block block){}

    public Block blockValidated(byte[] blockId){
        return null;
    }

    public Block blockInvalidated(byte[] blockId){
        return null;
    }

    public List<Block> getBlocksWithNum(int blockNum){
        return null;
    }

    public Block getBlockWithId(byte[] blockId){
        return null;
    }

    public Block getUnvalidatedBlockWithId(byte[] blockId){
        return null;
    }

    public void addMessage(ParsedMessage msg){}

    public boolean hashPrepare(int seq_num, int view, byte[] blockId){
        return true;
    }

    public List<ParsedMessage> getMessageOfTypeSeq(MessageType msg_type, int sequence_number){
        return null;
    }

    public List<ParsedMessage> getMessageOfTypeView(MessageType msg_type, int view){
        return null;
    }

    public List<ParsedMessage> getMessageOfTypeSeqView(MessageType msg_type, int sequence_number, int view){
        return null;
    }

    public List<ParsedMessage> getMessageOfTypeSeqViewBlock(MessageType msg_type, int sequence_number, int view, byte[] blockId){
        return null;
    }

    public void garbageCollect(int current_seq_num){}

    public void setMaxLogSize(int max_log_size){
        this.max_log_size = max_log_size;
    }
}
