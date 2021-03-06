package se.chalmers.datx02.FBA.message;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.*;
import se.chalmers.datx02.FBA.lib.exceptions.InvalidMessage;
import se.chalmers.datx02.FBA.lib.exceptions.SerializationError;
import se.chalmers.datx02.lib.models.PeerMessage;

public class ParsedMessage {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private byte[] header_bytes, header_signature, message_bytes;
    protected boolean from_self;

    private Object message;

    /**
     * Parses a ParsedMessage from PeerMessage
     * @param message specifies the PeerMessages
     * @param own_id specifies the peerId
     * @throws SerializationError
     * @throws InvalidMessage
     */
    public ParsedMessage(PeerMessage message, byte[] own_id) throws SerializationError, InvalidMessage {
        String messageType = message.getHeader().getMessageType();
        Object deserialized_message = null;

        switch(messageType){
            case "Seal":
                try {
                    deserialized_message = PbftSeal.parseFrom(message.getContent());
                } catch (InvalidProtocolBufferException e) {
                    throw new SerializationError("Error parsing Pbftseal");
                }
                break;
            case "NewView":
                try {
                    deserialized_message = PbftNewView.parseFrom(message.getContent());
                } catch (InvalidProtocolBufferException e) {
                    throw new SerializationError("Error parsing Pbftview");
                }
                break;
            default: // Parse to PbftMessage default
                try {
                    deserialized_message = PbftMessage.parseFrom(message.getContent());
                } catch (InvalidProtocolBufferException e) {
                    throw new SerializationError("Error parsing Pbftmessage");
                }
                break;
        }

        this.from_self = false;
        this.header_bytes = message.getHeaderBytes();
        this.header_signature = message.getHeadeerSignature();
        this.message = deserialized_message;
        this.message_bytes = message.getContent();

        // Check that they have same msg type
        if(deserialized_message instanceof PbftMessage) {
            if(((PbftMessage) deserialized_message).getInfo().getMsgType() != message.getHeader().getMessageType())
                throw new InvalidMessage("Message type mismatch: received a PeerMessage with type " + message.getHeader().getMessageType()
                        + " that contains a PBFT message with type " + ((PbftMessage) deserialized_message).getInfo().getMsgType());
        }
        else if(deserialized_message instanceof PbftSeal) {
            if(((PbftSeal) deserialized_message).getInfo().getMsgType() != message.getHeader().getMessageType())
                throw new InvalidMessage("Message type mismatch: received a PeerMessage with type " + message.getHeader().getMessageType()
                        + " that contains a PBFT message with type " + ((PbftSeal) deserialized_message).getInfo().getMsgType());
        }
        else if(deserialized_message instanceof PbftNewView) {
            if(((PbftNewView) deserialized_message).getInfo().getMsgType() != message.getHeader().getMessageType())
                throw new InvalidMessage("Message type mismatch: received a PeerMessage with type " + message.getHeader().getMessageType()
                        + " that contains a PBFT message with type " + ((PbftNewView) deserialized_message).getInfo().getMsgType());
        }

        this.from_self = (this.info().getSignerId().toByteArray() == own_id);
    }

    /**
     * Parses a ParsedMessage from PbftMessage
     * @param message PbftMessage to be parsed
     */
    public ParsedMessage(PbftMessage message){
        ByteString messageBytes = message.toByteString();

        this.from_self = true;
        this.header_bytes = new byte[]{};
        this.header_signature = new byte[]{};
        this.message = message;
        this.message_bytes = messageBytes.toByteArray();
    }

    /**
     * Parses a ParsedMessage from PbftNewView
     * @param message PbftNewView to be parsed
     */
    public ParsedMessage(PbftNewView message){
        ByteString messageBytes = message.toByteString();

        this.from_self = true;
        this.header_bytes = new byte[]{};
        this.header_signature = new byte[]{};
        this.message = message;
        this.message_bytes = messageBytes.toByteArray();
    }

    /**
     * Parses a ParsedMessage from PbftSignedVote
     * @param vote PbftSignedVote to be parsed
     * @throws SerializationError
     */
    public ParsedMessage(PbftSignedVote vote) throws SerializationError {
        PbftMessage message = null;
        try {
            message = PbftMessage.parseFrom(vote.getMessageBytes());
        } catch (InvalidProtocolBufferException e) {
            throw new SerializationError("Failed to parse vote to Pbftmessage");
        }

        this.from_self = true;
        this.header_bytes = vote.getHeaderBytes().toByteArray();
        this.header_signature = vote.getHeaderSignature().toByteArray();
        this.message = message;
        this.message_bytes = vote.getMessageBytes().toByteArray();
    }

    /**
     * Gets the PbftMessageInfo from message
     * @return returns message info
     */
    public PbftMessageInfo info(){
        if(message instanceof PbftMessage)
            return ((PbftMessage) message).getInfo();
        else if(message instanceof PbftSeal)
            return ((PbftSeal) message).getInfo();
        else if(message instanceof PbftNewView)
            return ((PbftNewView) message).getInfo();

        logger.error("Failed to get info for Pbft message");
        return null;
    }

    /**
     * Gets the blockId from message
     * @return returns the block id
     */
    public ByteString getBlockId(){
        if(message instanceof PbftMessage)
            return ((PbftMessage) message).getBlockId();
        else if(message instanceof PbftSeal)
            logger.warn("ParsedPeerMessage.get_block_id found a new view message!");
        else if(message instanceof PbftNewView)
            logger.warn("ParsedPeerMessage.get_block_id found a seal response message!");
        return null;
    }

    /**
     * Gets the PbftNewView from message
     * @return returns a PbftNewView object
     */
    public PbftNewView getNewViewMessage(){
        if(message instanceof PbftMessage)
            logger.warn("ParsedPeerMessage.get_view_change_message found a pbft message!");
        else if(message instanceof PbftSeal)
            logger.warn("ParsedPeerMessage.get_view_change_message found a new view message!");
        else if(message instanceof PbftNewView)
            return (PbftNewView) message;
        return null;
    }

    /**
     * Gets the PbftSeal from message
     * @return returns a PbftSeal object
     */
    public PbftSeal getSeal(){
        if(message instanceof PbftMessage)
            logger.warn("ParsedPeerMessage.get_seal found a pbft message!");
        else if(message instanceof PbftSeal)
            return (PbftSeal) message;
        else if(message instanceof PbftNewView)
            logger.warn("ParsedPeerMessage.get_seal found a new view message!");
        return null;
    }

    /**
     * Checks if message is from self
     * @return returns true if from self
     */
    public boolean fromSelf(){
        return from_self;
    }

    /**
     * Gets header bytes
     * @return returns the header bytes
     */
    public byte[] getHeaderBytes(){
        return header_bytes;
    }

    /**
     * Gets header signature
     * @return returns the header signature
     */
    public byte[] getHeaderSignature(){
        return header_signature;
    }

    /**
     * Gets message bytes
     * @return returns the message bytes
     */
    public byte[] getMessageBytes(){
        return message_bytes;
    }
}
