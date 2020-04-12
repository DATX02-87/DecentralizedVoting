package se.chalmers.datx02.FBA.message;


import com.google.protobuf.ByteString;
import pbft.sdk.protobuf.PbftMessage;
import pbft.sdk.protobuf.PbftMessageInfo;
import pbft.sdk.protobuf.PbftSeal;
import pbft.sdk.protobuf.PbftSignedVote;
import sawtooth.sdk.protobuf.ConsensusPeerMessage;

import static com.sun.org.apache.xerces.internal.impl.dv.util.HexBin.encode;

public class MessageExtension {
    /**
     * Logs a PBFT messageInfo
     * @param message to log
     */
    public static String logMessage(PbftMessageInfo message){
        return String.format("MsgInfo: (%s S %d V %d <- %s)",
                message.getMsgType(),
                message.getSeqNum(),
                message.getView(),
                encode(message.getSignerId().toByteArray()));
    }

    /**
     * Logs a PBFT messageSeal
     * @param message to log
     */
    public static String logMessage(PbftSeal message){
        StringBuilder returnString = new StringBuilder(String.format("PbftSeal(info: %s, block_id %s\n",
                logMessage(message.getInfo()),
                encode(message.getBlockId().toByteArray())));

        int i = 0;
        for(PbftSignedVote vote : message.getCommitVotesList()){
            returnString.append(String.format("%d header: %s msg:%s\n",
                    i++,
                    vote.getHeaderBytes(),
                    vote.getMessageBytes()));
        }

        return returnString.toString();
    }

    /**
     * Logs a PBFT signedVote
     * @param message to log
     */
    public static String logMessage(PbftSignedVote message){
        try {
            ConsensusPeerMessage headerByte = ConsensusPeerMessage.parseFrom(message.getHeaderBytes());
            PbftMessage messageByte = PbftMessage.parseFrom(message.getMessageBytes());

            return String.format("PbftSignedVote(header: %s, message: %s, header_bytes: %s, header_signature: %s, message_bytes: %s)",
                    headerByte,
                    messageByte,
                    encode(message.getHeaderBytes().toByteArray()),
                    encode(message.getHeaderSignature().toByteArray()),
                    encode(message.getMessageBytes().toByteArray()));
        }
        catch(Exception e){
            return "Failed to log Signedvote";
        }
    }

    /**
     * Creates a Pbft MessageInfo object
     * @return a PbftMessageInfo
     */
    public static PbftMessageInfo newMessageInfo(MessageType msg_type, long view, long seq_num, byte[] signer_id){
        PbftMessageInfo.Builder messageInfo = PbftMessageInfo.newBuilder();

        messageInfo.setMsgType(msg_type.toCorrectString());
        messageInfo.setView(view);
        messageInfo.setSeqNum(seq_num);
        messageInfo.setSignerId(ByteString.copyFrom(signer_id));

        return messageInfo.build();
    }
}
