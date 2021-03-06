package se.chalmers.datx02.PBFT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.PBFT.lib.timing.Timeout;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;

import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public enum Phase {
        PrePreparing,
        Preparing,
        Commiting,
        Finishing;

        private boolean finishing;

        public boolean getFinishing() {
            return finishing;
        }

        public void setFinishing(boolean newFinishing) {
            if (this == Finishing)
                finishing = newFinishing;
        }

        public static Phase setAndCreateFinishing(boolean newFinishing) {
            Phase returnValue = Phase.Finishing;
            returnValue.setFinishing(newFinishing);

            return returnValue;
        }
    }

    public enum Mode {
        Normal,
        ViewChanging;

        private long viewChanging;

        public long getViewChanging() {
            return viewChanging;
        }

        private void setViewChanging(long viewChanging) {
            this.viewChanging = viewChanging;
        }

        public static Mode changeToView(long viewChanging) {
            Mode returnValue = Mode.ViewChanging;
            returnValue.setViewChanging(viewChanging);

            return returnValue;
        }

        public static Mode changeToNormal() {
            return Mode.Normal;
        }
    }

    private byte[] peerId;
    private long seq_num, view;
    private byte[] chain_head;
    private Phase phase;
    private Mode mode;
    private List<byte[]> member_ids;
    private long faulty_nodes;
    protected Timeout idle_timeout, commit_timeout, view_change_timeout;
    protected Duration view_change_duration, exponential_retry_base, exponential_retry_max;

    private long forced_view_changed_interval;

    public State(byte[] peerId, long head_block_num, Config config) {
        long faultyNodes = (config.getMembersCount() - 1) / 3;

        if (faultyNodes == 0)
            logger.warn("This network does not contain enough nodes to be fault tolerant");

        this.peerId = peerId;
        this.seq_num = head_block_num + 1;
        this.view = 0;
        this.chain_head = null;
        this.phase = Phase.PrePreparing;
        this.mode = Mode.Normal;
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

    public byte[] getPrimaryId() {
        int primary_index = ((int) this.view) % this.member_ids.size();
        return member_ids.get(primary_index);
    }

    public byte[] getPrimaryIdAtView(long view) {
        int primary_index = ((int) view) % this.member_ids.size();
        return member_ids.get(primary_index);
    }


    public boolean isPrimary() {
        return Arrays.equals(this.peerId, getPrimaryId());
    }

    public boolean isPrimaryAtView(long view) {
        return Arrays.equals(this.peerId, getPrimaryIdAtView(view));
    }

    /**
     * Switch to the desired phase if it is the next phase of the algorithm
     *
     * @param desired_phase The desired next phase
     * @throws InternalError if not next phase of algorithm
     */
    public void switchPhase(Phase desired_phase, boolean newFinishing) throws InternalError {
        boolean is_next_phase = false;

        if (desired_phase == Phase.Finishing) {
            is_next_phase = true;
        } else {
            if (this.phase == Phase.PrePreparing && desired_phase == Phase.Preparing
                    || this.phase == Phase.Preparing && desired_phase == Phase.Commiting
                    || this.phase == Phase.Finishing && desired_phase == Phase.PrePreparing)
                is_next_phase = true;
        }

        if (is_next_phase) {
            logger.debug(this.toString() + " Changing to phase: " + desired_phase);
            if (desired_phase == Phase.Finishing)
                this.phase = Phase.setAndCreateFinishing(newFinishing); // Set finishing
            else
                this.phase = desired_phase;
        } else {
            throw new InternalError("Node is in " + this.phase + " phase; attempted to switch to " + desired_phase);
        }
    }

    public void forceSwitchPhase(Phase desiredPhase, boolean newFinishing) {
        if (desiredPhase == Phase.Finishing) {
            this.phase = Phase.setAndCreateFinishing(newFinishing);
        } else {
            this.phase = desiredPhase;
        }
    }

    public boolean atForcedViewChange() {
        return ((this.seq_num % this.forced_view_changed_interval) == 0);
    }

    public long getSeqNum() {
        return seq_num;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public void setSeqNum(long seq_num) {
        this.seq_num = seq_num;
    }

    public List<byte[]> getMembers() {
        return member_ids;
    }

    public void setMembers(List<byte[]> member_ids) {
        this.member_ids = member_ids;
    }

    public Mode getMode() {
        return mode;
    }

    public byte[] getPeerId() {
        return peerId;
    }

    public void setChainHead(byte[] newChainhead) {
        this.chain_head = newChainhead;
    }

    public byte[] getChainHead() {
        return this.chain_head;
    }

    public Phase getPhase() {
        return phase;
    }

    public long getFaultyNodes() {
        return faulty_nodes;
    }

    public void setFaultyNodes(long faulty_nodes) {
        this.faulty_nodes = faulty_nodes;
    }

    public void setModeNormal() {
        mode = Mode.changeToNormal();
    }

    public void setModeViewChanging(long view) {
        mode = Mode.changeToView(view);
    }

    @Override
    public String toString() {
        String is_primary = (isPrimary()) ? " *" : "";
        String phase = "";

        if (this.getMode() == Mode.ViewChanging)
            phase = String.format("V(%d)", this.getMode().getViewChanging());
        else {
            if (this.getPhase() == Phase.Finishing)
                phase = String.format("Fi(%b)", this.getPhase().getFinishing());
            else
                switch (this.getPhase()) {
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
        }
        return String.format("(%s, view %d, seq %d%s)",
                phase,
                this.getView(),
                this.getSeqNum(),
                is_primary);
    }
}
