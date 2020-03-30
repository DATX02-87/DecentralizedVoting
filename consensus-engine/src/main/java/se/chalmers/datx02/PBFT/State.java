package se.chalmers.datx02.PBFT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.PBFT.lib.Timeout;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;

import java.time.Duration;
import java.util.List;

public class State {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public enum Phase{
        PrePreparing,
        Preparing,
        Commiting,
        Finishing;

        public boolean finishing;
    }

    public enum Mode{
        Normal,
        ViewChanging;

        public long viewChanging;

        // TODO: Create method when changing view to also change long variable
    }

    private byte[] peerId;
    private long seq_num, view;
    private byte[] chain_head;
    private Phase phase;
    private Mode mode;
    private List<byte[]> member_ids;
    private long faulty_nodes;
    private Timeout idle_teamout, commit_timeout, view_change_timeout;
    private Duration view_change_duration, exponential_retry_base, exponential_retry_max;

    private long forced_view_changed_interval;

    public State(byte[] peerId, long head_block_num, Config config){
        long faultyNodes = (config.getMembersCount() - 1)/3;

        if(faultyNodes == 0)
            logger.warn("This network does not contain enough nodes to be fault tolerant");

        this.peerId = peerId;
        this.seq_num = head_block_num + 1;
        this.view = 0;
        this.chain_head = null;
        this.phase = Phase.PrePreparing;
        this.mode = Mode.Normal;
        this.faulty_nodes = faultyNodes;
        this.member_ids = config.getMembers();
        this.idle_teamout = new Timeout(config.idleTimeout);
        this.commit_timeout = new Timeout(config.commitTimeout);
        this.view_change_timeout = new Timeout(config.viewChangeDuration);
        this.view_change_duration = config.viewChangeDuration;
        this.exponential_retry_base = config.exponentialRetryBase;
        this.exponential_retry_max = config.exponentialRetryMax;
        this.forced_view_changed_interval = config.forcedViewChangeInterval;
    }

    public byte[] getPrimaryId(){
        int primary_index = ((int) this.view) % this.member_ids.size();
        return member_ids.get(primary_index);
    }

    public byte[] getPrimaryIdAtView(long view){
        int primary_index = ((int) view) % this.member_ids.size();
        return member_ids.get(primary_index);
    }


    public boolean isPrimary(){
        return (this.peerId == getPrimaryId());
    }

    public boolean isPrimaryAtView(long view){
        return (this.peerId == getPrimaryIdAtView(view));
    }

    /**
     * Switch to the desired phase if it is the next phase of the algorithm
     * @param desired_phase The desired next phase
     * @throws InternalError if not next phase of algorithm
     */
    public void switchPhase(Phase desired_phase) throws InternalError {
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
            this.phase = desired_phase;
        }
        else{
            throw new InternalError("Node is in " + this.phase + " phase; attempted to switch to " + desired_phase);
        }
    }

    public boolean at_forced_view_change(){
        return ((this.seq_num % this.forced_view_changed_interval) == 0);
    }

    public long getView(){
        return view;
    }

    public Mode getMode(){
        return mode;
    }

    public byte[] getPeerId(){
        return peerId;
    }

    @Override
    public String toString(){
        return "";
    }
}
