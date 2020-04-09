package se.chalmers.datx02.PBFT;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.PbftMessageInfo;
import sawtooth.sdk.protobuf.ConsensusBlock;
import se.chalmers.datx02.PBFT.message.MessageType;
import se.chalmers.datx02.PBFT.message.ParsedMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class MessageLog {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HashMap<byte[], ConsensusBlock> unvalidated_blocks;

    private HashSet<ConsensusBlock> blocks;
    private HashSet<ParsedMessage> messages;

    private long max_log_size;

    /**
     * Displays message log
     * @return returns display for message log
     */
    @Override
    public String toString(){
        List<PbftMessageInfo> msg_infos = messages.stream()
                .map(ParsedMessage::info)
                .collect(Collectors.toList());

        List<String> string_infos = msg_infos.stream()
                .map(info -> String.format("    {{ %s, view: {%d}, seq: {%d}, signer: {%s} }}",
                        info.getMsgType(),
                        info.getView(),
                        info.getSeqNum(),
                        HexBin.encode(info.getSignerId().toByteArray())))
                .collect(Collectors.toList());

        return "\nMessageLog:\n" + String.join("\n", string_infos);
    }

    /**
     * Creates a new message log
     * @param config specifies config to be used
     */
    public MessageLog(Config config){
        this.unvalidated_blocks = new HashMap<>();
        this.blocks = new HashSet<>();
        this.messages = new HashSet<>();
        this.max_log_size = config.getMaxLogSize();
    }

    /**
     * Adds a validated block
     * @param block specifies block to be added
     */
    public void addValidatedBlock(ConsensusBlock block){
        logger.trace("Adding validated block to log: " + block.toString());
        this.blocks.add(block);
    }

    /**
     * Adds an unvalidated block
     * @param block specifies block to be added
     */
    public void addUnvalidatedBlock(ConsensusBlock block){
        logger.trace("Adding unvalidated block to log: " + block.toString());
        this.unvalidated_blocks.put(block.getBlockId().toByteArray(), block);
    }

    /**
     * Checks if a block is validated
     * @param blockId specifies block id to be checked
     */
    public ConsensusBlock blockValidated(byte[] blockId){
        logger.trace("Marking block " + blockId + " as validated");

        ConsensusBlock block = unvalidated_blocks.get(blockId);
        if(block == null)
            return null;

        this.blocks.add(block);
        unvalidated_blocks.remove(blockId);

        return block;
    }

    /**
     * Checks if a block is invalidated
     * @param blockId specifies block id to be checked
     */
    public boolean blockInvalidated(byte[] blockId){
        logger.trace("Dropping invalited block: " + blockId);

        if(!this.unvalidated_blocks.containsKey(blockId))
            return false;

        this.unvalidated_blocks.remove(blockId);

        return true;
    }

    /**
     * Gets all blocks with num
     * @param blockNum specifies the num to be searched
     * @return returns list with blocks with num
     */
    public List<ConsensusBlock> getBlocksWithNum(long blockNum){
        List<ConsensusBlock> listReturn = new ArrayList<>();

        for(ConsensusBlock block : blocks){
            if(block.getBlockNum() == blockNum)
                listReturn.add(block);
        }

        return listReturn;
    }

    /**
     * Get all blocks with id
     * @param blockId specifies id to be searched
     * @return returns all blocks with id
     */
    public ConsensusBlock getBlockWithId(byte[] blockId){
        ConsensusBlock blockReturn = null;

        for(ConsensusBlock block : blocks){
            if(block.getBlockId().toByteArray() == blockId){
                blockReturn = block;
                break;
            }
        }

        return blockReturn;
    }

    /**
     * Gets all unvalidated blocks with id
     * @param blockId specifies id to be searched
     * @return returns all unvalidated blocks with id
     */
    public ConsensusBlock getUnvalidatedBlockWithId(byte[] blockId){
        return this.unvalidated_blocks.get(blockId);
    }

    /**
     * Adds a message to the log
     * @param msg specifies message to be added
     */
    public void addMessage(ParsedMessage msg){
        logger.trace("Adding message to log: " + msg.toString());
        this.messages.add(msg);
    }

    /**
     * Check if the log has a PrePrepare at the given view and sequence number that matches the given block ID
     * @param seq_num specifies seqNum
     * @param view specifies view
     * @param blockId specifies block id
     * @return returns true if successfully finds a block
     */
    public boolean hashPrePrepare(long seq_num, long view, byte[] blockId){
        List<ParsedMessage> list = getMessageOfTypeSeqView(MessageType.PrePrepare, seq_num, view);

        for(ParsedMessage msg : list){
            if(msg.getBlockId().toByteArray() == blockId)
                return true;
        }

        return false;
    }

    /**
     * Gets all messages with specific msg type and sequence number
     * @param msg_type specifies message type
     * @param sequence_number specifies sequence number
     * @return returns messages with msgtype and seqnum
     */
    public List<ParsedMessage> getMessageOfTypeSeq(MessageType msg_type, long sequence_number){
        List<ParsedMessage> list = new ArrayList<>();

        for(ParsedMessage msg : messages){
            if(msg.info().getMsgType() == msg_type.toString()
                    && msg.info().getSeqNum() == sequence_number)
                list.add(msg);
        }

        return list;
    }

    /**
     * Gets all messages with specific msg typ and view
     * @param msg_type specifies message type
     * @param view specifies view
     * @return returns all messages with msgtype and view
     */
    public List<ParsedMessage> getMessageOfTypeView(MessageType msg_type, long view){
        List<ParsedMessage> list = new ArrayList<>();

        for(ParsedMessage msg : messages){
            if(msg.info().getMsgType() == msg_type.toString()
                    && msg.info().getView() == view)
                list.add(msg);
        }

        return list;
    }

    /**
     * Gets all message with specific msg type, view and sequence number
     * @param msg_type specifies message type
     * @param sequence_number specifies sequence number
     * @param view specifies view
     * @return returns all messages found
     */
    public List<ParsedMessage> getMessageOfTypeSeqView(MessageType msg_type, long sequence_number, long view){
        List<ParsedMessage> list = new ArrayList<>();

        for(ParsedMessage msg : messages){
            if(msg.info().getMsgType() == msg_type.toString()
                    && msg.info().getSeqNum() == sequence_number
                    && msg.info().getView() == view)
                list.add(msg);
        }

        return list;
    }

    /**
     * Get all message with msgtype, seq num, view and block id
     * @param msg_type specifies message type
     * @param sequence_number specifies sequence number
     * @param view specifies view
     * @param blockId specifies block id
     * @return returns all found messages
     */
    public List<ParsedMessage> getMessageOfTypeSeqViewBlock(MessageType msg_type, long sequence_number, long view, byte[] blockId){
        List<ParsedMessage> list = new ArrayList<>();

        for(ParsedMessage msg : messages){
            if(msg.info().getMsgType() == msg_type.toString()
                    && msg.info().getSeqNum() == sequence_number
                    && msg.info().getView() == view
                    && msg.getBlockId().toByteArray() == blockId)
                list.add(msg);
        }

        return list;
    }

    /**
     * Garbage collect the log if it has reached the `max_log_size`
     * @param current_seq_num specifies current sequence number
     */
    public void garbageCollect(long current_seq_num){
        if(messages.size() >= max_log_size){
            // REMOVE OLD MESSAGES
            for(ParsedMessage msg : messages){
                if(msg.info().getSeqNum() < current_seq_num - 1)
                    messages.remove(msg);
            }

            // REMOVE OLD BLOCKS
            for(ConsensusBlock block : blocks){
                if(block.getBlockNum() < current_seq_num - 1)
                    blocks.remove(block);
            }
        }
    }

    /**
     * Sets the max log size
     * @param max_log_size specifies new max log size
     */
    public void setMaxLogSize(long max_log_size){
        this.max_log_size = max_log_size;
    }
}
