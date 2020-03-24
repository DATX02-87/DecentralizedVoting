package se.chalmers.datx02.PBFT.message;


import com.google.protobuf.ByteString;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.PbftMessage;
import pbft.sdk.protobuf.PbftMessageInfo;
import pbft.sdk.protobuf.PbftSeal;
import pbft.sdk.protobuf.PbftSignedVote;
import sawtooth.sdk.protobuf.ConsensusPeerMessage;

public class MessageExtension {
    private static final Logger logger = LoggerFactory.getLogger(MessageExtension.class);

    // TODO: Do we need new hash functions?
    public static int hash(){
        return 0;
    }

    /**
     * Logs a PBFT messageInfo
     * @param message to log
     */
    public static void logMessage(PbftMessageInfo message){
        logger.info("MsgInfo: ("+ message.getMsgType() +
                " S " + message.getSeqNum() +
                " V " + message.getView() +
                " <- " + HexBin.encode(message.getSignerId().toByteArray()) + ")");
    }

    /**
     * Logs a PBFT messageSeal
     * @param message to log
     */
    public static void logMessage(PbftSeal message){
        logger.info("PbftSeal(info: "+ message.getInfo() +
                ", block_id " + HexBin.encode(message.getBlockId().toByteArray()));

        int i = 0;
        for(PbftSignedVote vote : message.getCommitVotesList()){
            logger.info(i++ + " header: " + vote.getHeaderBytes() + " msg:" + vote.getMessageBytes());
        }
    }

    /**
     * Logs a PBFT signedVote
     * @param message to log
     */
    public static void logMessage(PbftSignedVote message){

        try {
            ConsensusPeerMessage headerByte = ConsensusPeerMessage.parseFrom(message.getHeaderBytes());
            PbftMessage messageByte = PbftMessage.parseFrom(message.getMessageBytes());

            logger.info("PbftSignedVote(header: "+ headerByte +
                    ", message: " +  messageByte +
                    ", header_bytes: " +  HexBin.encode(message.getHeaderBytes().toByteArray()) +
                    ", header_signature: " +  HexBin.encode(message.getHeaderSignature().toByteArray()) +
                    ", message_bytes: " + HexBin.encode(message.getMessageBytes().toByteArray()) + ")");
        }
        catch(Exception e){
            logger.error("Failed to log Signedvote");
        }

    }

    /**
     * Creates a Pbft MessageInfo object
     * @return a PbftMessageInfo
     */
    public static PbftMessageInfo newMessageInfo(MessageType msg_type, long view, long seq_num, byte[] signer_id){
        PbftMessageInfo.Builder messageInfo = PbftMessageInfo.newBuilder();

        messageInfo.setMsgType(msg_type.toString());
        messageInfo.setView(view);
        messageInfo.setSeqNum(seq_num);
        messageInfo.setSignerId(ByteString.copyFrom(signer_id));

        return messageInfo.build();
    }
}
