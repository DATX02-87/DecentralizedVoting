package se.chalmers.datx02.FBA.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The different message types a message can have
 */
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

    private static final Logger logger = LoggerFactory.getLogger(MessageType.class.getClass());

    /**
     * Gets the string value of a message type
     * @return returns the display value of a message type
     */
    @Override
    public String toString(){
        String toDisplay = "";
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

        return toDisplay;
    }

    public String toCorrectString(){
        String toDisplay = "";
        switch (this) {
            case PrePrepare: toDisplay = "PrePrepare"; break;
            case Prepare: toDisplay = "Prepare"; break;
            case Commit: toDisplay = "Commit"; break;
            case NewView: toDisplay = "NewView"; break;
            case ViewChange: toDisplay = "ViewChange"; break;
            case SealRequest: toDisplay = "SealRequest"; break;
            case Seal: toDisplay = "Seal"; break;
            case Unset: toDisplay = "Unset"; break;
            default: throw new IllegalStateException();
        }

        return toDisplay;
    }

    /**
     * Parses a message type from string
     * @param msg_type specifies the string to be parsed
     * @return returns the parsed message type
     */
    public static MessageType from(String msg_type){
        switch (msg_type) {
            case "PrePrepare": return PrePrepare;
            case "Prepare": return Prepare;
            case "Commit": return Commit;
            case "NewView": return NewView;
            case "ViewChange": return ViewChange;
            case "SealRequest": return SealRequest;
            case "Seal": return Seal;
            default:
                logger.warn("Unhandled PBFT message type: " + msg_type);
                return Unset;
        }
    }

    /**
     * Gets the string value of a message type
     * @param msg_type specifies the message type to be displayed
     * @return returns the display value of a message type
     */
    public static String from(MessageType msg_type){
        return msg_type.toString();
    }
}
