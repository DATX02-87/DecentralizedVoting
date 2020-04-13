package se.chalmers.datx02.PBFT.message;

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
    Unset
}
