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
        Normal;

        public int ViewChanging;
    }

    // TODO: Impl.

    private byte[] peerId;
    private int seq_num, view;
    private byte[] chain_head;
    private Phase phase;
    private Mode mode;
    private List<byte[]> member_ids;
    private int faulty_nodes;
    private Timeout idle_teamout, commit_timeout, view_change_timeout;
    private Duration view_change_duration, exponential_retry_base, exponential_retry_max;

    private int forced_view_changed_interval;

    public State(byte[] peerId, long head_block_num, Config config){

    }

    public byte[] getPrimaryId(){
        return null;
    }

    public byte[] getPrimaryIdAtView(int view){
        return null;
    }


    public boolean isPrimary(){
        return true;
    }

    public boolean isPrimaryAtView(int view){
        return true;
    }

    public void switchPhase(Phase desired_phase){}
}
