package se.chalmers.datx02.PBFT;

import se.chalmers.datx02.PBFT.lib.Timeout;

import java.time.Duration;
import java.util.List;

public class State {
    public enum Phase{
        PrePreparing,
        Preparing,
        Commiting;

        public boolean Finishing;
    }

    public enum Mode{
        Normal,
        ViewChanging;

        public long viewChanging;

        // TODO: Create method when chaning view to also change long variable
    }

    // TODO: Impl.

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

    }

    public byte[] getPrimaryId(){
        return null;
    }

    public byte[] getPrimaryIdAtView(long view){
        return null;
    }


    public boolean isPrimary(){
        return true;
    }

    public boolean isPrimaryAtView(long view){
        return true;
    }

    public void switchPhase(Phase desired_phase){}

    public long getView(){
        return view;
    }

    public Mode getMode(){
        return mode;
    }

    @Override
    public String toString(){
        return "";
    }
}
