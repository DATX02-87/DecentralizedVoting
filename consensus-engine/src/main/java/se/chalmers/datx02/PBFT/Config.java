package se.chalmers.datx02.PBFT;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class Config {

    // TODO: Implementation

    private List<String> members;
    private HashMap<String, String> settings;

    private Duration block_publishing_delay,
            update_recv_timeout,
            exponential_retry_base,
            exponential_retry_max,
            idle_timeout,
            commit_timeout,
            view_change_duration;

    private int forced_view_change_interval,
                max_log_size;

    private String storage_location;

    public void defaultSettings(){ }

    public void loadSettings(byte[] blockId, se.chalmers.datx02.lib.Service service){ }

    public void mergeSetting(String field, String key){ }

    public void mergeMillisSetting(Duration field, String key){}

    public List<String> getMembersFromSettings(){return null;}

    public int getMaxLogSize(){
        return this.max_log_size;
    }
}
