package se.chalmers.datx02.lib;

import com.google.protobuf.Descriptors;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusPeerInfo;
import sawtooth.sdk.protobuf.ConsensusRegisterResponse;

import java.util.List;

public class StartupState {
    private final ConsensusBlock chainHead;
    private final List<ConsensusPeerInfo> peers;
    private final ConsensusPeerInfo localPeerInfo;

    public StartupState(ConsensusBlock chainHead, List<ConsensusPeerInfo> peers, ConsensusPeerInfo localPeerInfo) {
        this.chainHead = chainHead;
        this.peers = peers;
        this.localPeerInfo = localPeerInfo;
    }

    public ConsensusBlock getChainHead() {
        return chainHead;
    }

    public List<ConsensusPeerInfo> getPeers() {
        return peers;
    }

    public ConsensusPeerInfo getLocalPeerInfo() {
        return localPeerInfo;
    }

    @Override
    public String toString() {
        return "StartupState{" +
                "chainHead=" + chainHead +
                ", peers=" + peers +
                ", localPeerInfo=" + localPeerInfo +
                '}';
    }
}
