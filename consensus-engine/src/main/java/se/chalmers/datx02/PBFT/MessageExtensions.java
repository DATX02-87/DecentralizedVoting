package se.chalmers.datx02.PBFT;

public enum MessageExtensions {
    MessageInfo,
    Message,
    Seal,
    SignedVote,
    NewView;

    // TODO: Implement hash & toString/display methods
    // TODO: How to extend SDK messagetypes?
}
