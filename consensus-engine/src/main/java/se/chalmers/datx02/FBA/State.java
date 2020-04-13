package se.chalmers.datx02.FBA;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.FBA.lib.exceptions.InternalError;
import se.chalmers.datx02.FBA.lib.timing.Timeout;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Hold the phase that the node is currently at
     */
    public enum Phase{
        PrePreparing,
        Preparing,
        Voting,
        Commiting, // todo: Add our phases
        Finishing;

        private votingThreshold thresHold;

        public votingThreshold getThresHold(){
            return thresHold;
        }

        private void setThresHold(votingThreshold thresHold){
            this.thresHold = thresHold;
        }

        public static Phase changeToVoting(votingThreshold thresHold){
            Phase returnValue = Phase.Voting;
            returnValue.setThresHold(thresHold);

            return returnValue;
        }
    }

    public enum votingThreshold{
        FIRST(0.5),
        SECOND(0.6),
        THIRD(0.7),
        FOURTH(0.8);

        private double percentage;

        votingThreshold(double percentage){
            this.percentage = percentage;
        }

        public double getPercentage(){
            return percentage;
        }
    }

    private byte[] peerId;
    private long seq_num;
    private byte[] chain_head;
    private Phase phase;
    private List<byte[]> member_ids;
    private long faulty_nodes;
    protected Timeout idle_timeout, commit_timeout, view_change_timeout;
    protected Duration view_change_duration, exponential_retry_base, exponential_retry_max;

    protected Timeout voting_timeout;


    private long forced_view_changed_interval;

    public State(byte[] peerId, long head_block_num, Config config){
        long faultyNodes = (config.getMembersCount() - 1)/3;

        if(faultyNodes == 0)
            logger.warn("This network does not contain enough nodes to be fault tolerant");

        this.peerId = peerId;
        this.seq_num = head_block_num + 1;
        this.chain_head = null;
        this.phase = Phase.PrePreparing;
        this.faulty_nodes = faultyNodes;
        this.member_ids = config.getMembers();
        this.idle_timeout = new Timeout(config.idleTimeout);
        this.commit_timeout = new Timeout(config.commitTimeout);
        this.view_change_timeout = new Timeout(config.viewChangeDuration);
        this.view_change_duration = config.viewChangeDuration;
        this.exponential_retry_base = config.exponentialRetryBase;
        this.exponential_retry_max = config.exponentialRetryMax;
        this.forced_view_changed_interval = config.forcedViewChangeInterval;
    }

    /**
     * Switch to the desired phase if it is the next phase of the algorithm
     * @param desired_phase The desired next phase
     * @throws InternalError if not next phase of algorithm
     */
    public void switchPhase(Phase desired_phase, boolean newFinishing) throws InternalError {
        boolean is_next_phase = false;

        if(desired_phase == Phase.Finishing) {
            is_next_phase = true;
        }
        else{
            if(this.phase == Phase.PrePreparing && desired_phase == Phase.Preparing
                    || this.phase == Phase.Preparing && desired_phase == Phase.Commiting
                    || this.phase == Phase.Finishing && desired_phase == Phase.PrePreparing )
                is_next_phase = true;
        }

        if(is_next_phase){
            logger.debug(this.toString() + " Changing to phase: " + desired_phase);
            if(desired_phase == Phase.Finishing)
                this.phase = Phase.setAndCreateFinishing(newFinishing); // Set finishing
            else
                this.phase = desired_phase;
        }
        else{
            throw new InternalError("Node is in " + this.phase + " phase; attempted to switch to " + desired_phase);
        }
    }

    public boolean atForcedViewChange(){
        return ((this.seq_num % this.forced_view_changed_interval) == 0);
    }

    public long getSeqNum(){
        return seq_num;
    }

    public void setSeqNum(long seq_num){
        this.seq_num = seq_num;
    }

    public List<byte[]> getMembers(){
        return member_ids;
    }

    public void setMembers(List<byte[]> member_ids){
        this.member_ids = member_ids;
    }

    public byte[] getPeerId(){
        return peerId;
    }

    public void setChainHead(byte[] newChainhead){
        this.chain_head = newChainhead;
    }

    public byte[] getChainHead(){
        return this.chain_head;
    }

    public Phase getPhase(){
        return phase;
    }

    public long getFaultyNodes(){
        return faulty_nodes;
    }

    public void setFaultyNodes(long faulty_nodes){
        this.faulty_nodes = faulty_nodes;
    }

    public void setPhaseVoting(votingThreshold thresHold){
        phase = phase.changeToVoting(thresHold);
    }

    @Override
    public String toString(){
        String phase = "";

        if(this.getPhase() == Phase.Finishing)
            phase = String.format("Fi(%b)", this.getPhase().getFinishing());
        else
            switch(this.getPhase()){
                case PrePreparing:
                    phase = "PP";
                    break;
                case Preparing:
                    phase = "Pr";
                    break;
                case Commiting:
                    phase = "Co";
                    break;
            }

        return String.format("(%s, seq %d%s), ID: %s",
                phase,
                this.getSeqNum(),
                HexBin.encode(peerId));
    }
}
