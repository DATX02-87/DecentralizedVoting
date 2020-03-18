package se.chalmers.datx02.PBFT;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void defaultSettings(){
        this.members = new ArrayList<>();
        this.block_publishing_delay = Duration.ofMillis(1000);
        this.update_recv_timeout = Duration.ofMillis(10);
        this.exponential_retry_base = Duration.ofMillis(100);
        this.exponential_retry_max = Duration.ofMillis(60000);
        this.idle_timeout = Duration.ofMillis(30000);
        this.commit_timeout = Duration.ofMillis(10000);
        this.view_change_duration = Duration.ofMillis(5000);
        this.forced_view_change_interval = 100;
        this.max_log_size = 10000;
        this.storage_location = "memory";
    }

    public void loadSettings(byte[] blockId, se.chalmers.datx02.lib.Service service){
        Map<String, String> settings = new HashMap<>();
        try {
            
        } catch (Exception e) {

        }
    }

    public void mergeSetting(String field, String key){
        //TODO
    }

    public void mergeMillisSetting(Duration field, String key){
        //TODO
    }

    public List<String> getMembersFromSettings(){
        //TODO
        return null;
    }

    public int getMaxLogSize() {
        return this.max_log_size;
    }
}
