package se.chalmers.datx02.PBFT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.PBFT.lib.timing.RetryUntilOk;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Config {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<byte[]> members;
    private Map<String, String> settings;

    // TODO: Create methods for changing these, dont leave as public
    public Duration
            blockPublishingDelay,   // How long to wait in between trying to publish blocks
            updateRecvTimeout,      // How long to wait for an update to arrive from the validator
            exponentialRetryBase,   // The base time to use for retrying with exponential backoff
            exponentialRetryMax,    // The maximum time for retrying with exponential backoff
            idleTimeout,            // How long to wait for the next BlockNew + PrePrepare before determining primary is faulty
            commitTimeout,          // How long to wait (after Pre-Preparing) for the node to commit the block before starting a
            viewChangeDuration;     // view change (guarantees liveness by allowing the network to get "unstuck" if it is unable

    public long forcedViewChangeInterval,
                maxLogSize;

    private String storageLocation;

    /**
     * Load default settings
     */
    public void defaultSettings(){
        this.members = new ArrayList<>();
        this.blockPublishingDelay = Duration.ofMillis(1000);
        this.updateRecvTimeout = Duration.ofMillis(10);
        this.exponentialRetryBase = Duration.ofMillis(100);
        this.exponentialRetryMax = Duration.ofMillis(60000);
        this.idleTimeout = Duration.ofMillis(30000);
        this.commitTimeout = Duration.ofMillis(10000);
        this.viewChangeDuration = Duration.ofMillis(5000);
        this.forcedViewChangeInterval = 100;
        this.maxLogSize = 10000;
        this.storageLocation = "memory";
    }

    /**
     * Load configuration from on-chain Sawtooth settings.
     * @param blockId
     * @param service
     * @throws UnknownBlockException
     * @throws ReceiveErrorException
     * @throws InterruptedException
     */
    public void loadSettings(byte[] blockId, se.chalmers.datx02.lib.Service service) throws InterruptedException {
        RetryUntilOk retryUntilOk = new RetryUntilOk(exponentialRetryBase, exponentialRetryMax);
        while (true) {
            try {
                List<String> inSettings = new ArrayList<>();
                inSettings.add("sawtooth.consensus.pbft.members");
                inSettings.add("sawtooth.consensus.pbft.block_publishing_delay");
                inSettings.add("sawtooth.consensus.pbft.idle_timeout");
                inSettings.add("sawtooth.consensus.pbft.commit_timeout");
                inSettings.add("sawtooth.consensus.pbft.view_change_duration");
                inSettings.add("sawtooth.consensus.pbft.forced_view_change_interval");
                this.settings = service.getSettings(blockId, inSettings);
                break;
            } catch (Exception e) {
                Thread.sleep(retryUntilOk.getDelay());
                retryUntilOk.check();
            }
        }

        this.members = getMembersFromSettings();

        mergeMillisSettingIfSet(blockPublishingDelay, "sawtooth.consensus.pbft.block_publishing_delay");
        mergeMillisSettingIfSet(idleTimeout, "sawtooth.consensus.pbft.idle_timeout");
        mergeMillisSettingIfSet(commitTimeout, "sawtooth.consensus.pbft.commit_timeout");
        mergeMillisSettingIfSet(viewChangeDuration, "sawtooth.consensus.pbft.view_change_duration");

        if(blockPublishingDelay.compareTo(idleTimeout) >= 0)
            logger.warn("Block publishing delay " + blockPublishingDelay.toString() + " must be less than " +
                    "than the idle timeout " + idleTimeout.toString());


        mergeSettingIfSet(forcedViewChangeInterval, "sawtooth.consensus.pbft.forced_view_change_interval");
    }


    public <T> void mergeSettingIfSet(T settingField, String settingKey){
        mergeSettingIfSetAndMap(settingField, settingKey, x -> x);
    }

    public <U, T> void mergeSettingIfSetAndMap(
            U settingsField,
            String settingsKey,
            Function<T, U> mapper
    ) {
        if(settings.containsKey(settingsKey)){
            T setting = (T) settings.get(settingsKey);
            U settingMapped = mapper.apply(setting);

            // TODO: Double check this shiet, might be wrong
            settings.replace(settingsKey, String.valueOf(settingsField), String.valueOf(settingMapped));
        }
    }

    public void mergeMillisSettingIfSet(Duration settingField, String settingKey){
        mergeSettingIfSetAndMap(settingField, settingKey, x -> Duration.ofMillis((long) x));
    }

    public List<byte[]> getMembersFromSettings() {
        String members_setting_value = settings.get("sawtooth.consensus.pbft.members");

        if(members_setting_value == null){
            logger.warn("'sawtooth.consensus.pbft.members' is empty; this setting must exist to use PBFT");
            return null;
        }

        // Parse String to json
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> members = mapper.readValue(members_setting_value, List.class);

            List<byte[]> membersMapped = members.stream().map(HexBin::decode).collect(Collectors.toList());

            return membersMapped;
        } catch (JsonProcessingException e) {
            logger.error("Unable to parse value at 'sawtooth.consensus.pbft.members' due to error: " + e);
            return null;
        }
    }

    public long getMaxLogSize() {
        return this.maxLogSize;
    }

    public void setMaxLogSize(long maxLogSize){
        this.maxLogSize = maxLogSize;
    }

    public String getStorageLocation(){
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation){
        this.storageLocation = storageLocation;
    }

    public Duration getBlockPublishingDelay() {
        return blockPublishingDelay;
    }

    public int getMembersCount(){
        return members.size();
    }

    public List<byte[]> getMembers(){
        return members;
    }
}
