package se.chalmers.datx02.FBA;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.FBA.lib.Storage;
import se.chalmers.datx02.FBA.lib.exceptions.InternalError;
import se.chalmers.datx02.FBA.lib.exceptions.StoredInMemory;
import se.chalmers.datx02.FBA.lib.timing.RetryUntilOk;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Config implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final Logger logger_static = LoggerFactory.getLogger(Config.class.getClass());

    private List<byte[]> members; // List of all members in peer network

    private List<byte[]> UNL; // List of all "trusted" members in peer network

    private Map<String, String> settings;

    private String name; // Name of node

    protected Duration
            blockPublishingDelay,   // How long to wait in between trying to publish blocks
            updateRecvTimeout,      // How long to wait for an update to arrive from the validator
            exponentialRetryBase,   // The base time to use for retrying with exponential backoff
            exponentialRetryMax,    // The maximum time for retrying with exponential backoff
            idleTimeout,            // How long to wait for the next BlockNew + PrePrepare before determining primary is faulty
            commitTimeout,          // How long to wait (after Pre-Preparing) for the node to commit the block before starting a
            viewChangeDuration;     // view change (guarantees liveness by allowing the network to get "unstuck" if it is unable

    protected long forcedViewChangeInterval,
                maxLogSize;

    private String storageLocation;

    /**
     * Creates a config with the default settings
     */
    public Config(){
        // Load settings
        defaultSettings();
    }

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
    public void loadSettings(byte[] blockId, se.chalmers.datx02.lib.Service service) {
        RetryUntilOk retryUntilOk = new RetryUntilOk(exponentialRetryBase, exponentialRetryMax);

        List<String> inSettings = Arrays.asList(
                "sawtooth.consensus.fba.members",
                "sawtooth.consensus.fba.block_publishing_delay",
                "sawtooth.consensus.fba.idle_timeout",
                "sawtooth.consensus.fba.commit_timeout",
                "sawtooth.consensus.fba.view_change_duration",
                "sawtooth.consensus.fba.forced_view_change_interval");

        this.settings = retryUntilOk.run(() -> service.getSettings(blockId, inSettings));

        this.members = getMembersFromSettings(settings);

        try {
            blockPublishingDelay = mergeMillisSettingIfSet("sawtooth.consensus.fba.block_publishing_delay");
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
        }
        try{
            idleTimeout = mergeMillisSettingIfSet("sawtooth.consensus.fba.idle_timeout");
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
        }
        try{
            commitTimeout = mergeMillisSettingIfSet("sawtooth.consensus.fba.commit_timeout");
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
        }
        try{
            viewChangeDuration = mergeMillisSettingIfSet("sawtooth.consensus.fba.view_change_duration");
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
        }

        if(blockPublishingDelay.compareTo(idleTimeout) >= 0)
            logger.warn("Block publishing delay " + blockPublishingDelay.toString() + " must be less than " +
                    "than the idle timeout " + idleTimeout.toString());

        try{
            forcedViewChangeInterval = mergeSettingIfSet("sawtooth.consensus.fba.forced_view_change_interval");
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
        }
    }


    public <R> R mergeSettingIfSet(String settingKey) throws InternalError {
        return (R) mergeSettingIfSetAndMap(settingKey, x -> Long.parseLong(x));
    }

    /**
     * Gets settings from map via String and then applies parsing function to it
     * @param settingsKey specifies the key to search
     * @param mapper specifies the mapper function
     * @param <R> specifies the generic return
     * @return returns a mapped setting element
     * @throws InternalError is thrown if no setting is found
     */
    public <R> R mergeSettingIfSetAndMap(
            String settingsKey,
            Function<String, R> mapper
    ) throws InternalError {
        if(settings.containsKey(settingsKey)){
            String setting = settings.get(settingsKey);

            return mapper.apply(setting);
        }
        throw new InternalError("Failed to get setting for key: " + settingsKey);
    }

    public Duration mergeMillisSettingIfSet(String settingKey) throws InternalError {
        return mergeSettingIfSetAndMap(settingKey, x -> Duration.ofMillis(Long.parseLong(x)));
    }

    public static List<byte[]> getMembersFromSettings(Map<String, String> settings) {
        String members_setting_value = settings.get("sawtooth.consensus.fba.members");

        if(members_setting_value == null){
            logger_static.warn("'sawtooth.consensus.fba.members' is empty; this setting must exist to use PBFT");
            return null;
        }

        // Parse String to json
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> members = mapper.readValue(members_setting_value, List.class);

            List<byte[]> membersMapped = members.stream().map(HexBin::decode).collect(Collectors.toList());

            return membersMapped;
        } catch (JsonProcessingException e) {
            logger_static.error("Unable to parse value at 'sawtooth.consensus.fba.members' due to error: " + e);
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

    public void loadUNL() throws InternalError {
        try {
            Storage.get_storage("", "UNLlist");
        } catch (IOException | ClassNotFoundException | StoredInMemory e) {
            throw new InternalError("Could not load UNL due to error: " + e);
        }
    }

    public List<byte[]> getMembers(){
        return members;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setName(byte[] peerId){
        // try to set peer id as name if name is empty
        if("".equals(name)){
            name = "FBA Node: " + HexBin.encode(peerId);
        }
    }
}

