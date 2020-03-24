package se.chalmers.datx02.PBFT.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MessageType {
    /// Basic message types for the multicast protocol
    PrePrepare,
    Prepare,
    Commit,

    /// Auxiliary PBFT messages
    NewView,
    ViewChange,
    SealRequest,
    Seal,

    Unset;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void display(){
        String toDisplay;
        switch (this) {
            case PrePrepare: toDisplay = "PP"; break;
            case Prepare: toDisplay = "Pr"; break;
            case Commit: toDisplay = "Co"; break;
            case NewView: toDisplay = "NV"; break;
            case ViewChange: toDisplay = "VC"; break;
            case SealRequest: toDisplay = "SR"; break;
            case Seal: toDisplay = "Se"; break;
            case Unset: toDisplay = "Un"; break;
            default: throw new IllegalStateException();
        }

        logger.info(toDisplay);
    }
    public MessageType from(String msg_type){
        switch (msg_type) {
            case "PrePrepare": return PrePrepare;
            case "Prepare": return Prepare;
            case "Commit": return Commit;
            case "NewView": return NewView;
            case "ViewChange": return ViewChange;
            case "SealRequest": return SealRequest;
            case "Seal": return Seal;
            default:
                logger.error("Unhandled PBFT message type: " + msg_type);
                return Unset;
        }
    }

    public String from(MessageType msg_type){
        return msg_type.toString();
    }
}
