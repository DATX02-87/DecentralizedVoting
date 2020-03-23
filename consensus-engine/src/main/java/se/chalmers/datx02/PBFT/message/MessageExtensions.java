package se.chalmers.datx02.PBFT.message;

public enum MessageExtensions {
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
