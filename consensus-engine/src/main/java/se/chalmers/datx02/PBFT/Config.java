package se.chalmers.datx02.PBFT;

import com.google.protobuf.Any;
import com.sun.tools.javac.main.Main;
import com.sun.tools.javac.main.Option;
import javafx.util.StringConverter;
import sawtooth.sdk.protobuf.ConsensusSettingsEntry;
import sawtooth.sdk.protobuf.Setting;
import se.chalmers.datx02.PBFT.lib.RetryUntilOk;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Config {

    // TODO: Implementation

    private List<String> members;
    private Map<String, String> settings;
    private Service service;

    private Duration
            blockPublishingDelay, // How long to wait in between trying to publish blocks
            updateRecvTimeout,    // How long to wait for an update to arrive from the validator
            exponentialRetryBase, // The base time to use for retrying with exponential backoff
            exponentialRetryMax,  // The maximum time for retrying with exponential backoff
            idleTimeout,           // How long to wait for the next BlockNew + PrePrepare before determining primary is faulty
            commitTimeout,         // How long to wait (after Pre-Preparing) for the node to commit the block before starting a
                                    // view change (guarantees liveness by allowing the network to get "unstuck" if it is unable
            viewChangeDuration;   //

    private long forcedViewChangeInterval;
    private int maxLogSize;

    private String storageLocation;

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

    public void loadSettings(byte[] blockId, se.chalmers.datx02.lib.Service service) throws UnknownBlockException, ReceiveErrorException, InterruptedException {
        RetryUntilOk retryUntilOk = new RetryUntilOk(exponentialRetryBase, exponentialRetryMax);

        while (true) {
            try {
                List<String> inSettings = new ArrayList<>();
                inSettings.add(members.toString());
                inSettings.add(blockPublishingDelay.toString());
                inSettings.add(idleTimeout.toString());
                inSettings.add(commitTimeout.toString());
                inSettings.add(viewChangeDuration.toString());
                inSettings.add(Long.toString(forcedViewChangeInterval));
                this.settings = service.getSettings(blockId, inSettings);

                break;
            } catch (Exception e) {
                Thread.sleep(retryUntilOk.getDelay());
                retryUntilOk.check();
            }
        }

        // TODO
    }


    public <T> void mergeSetting(
            Map<String, String> settingsMap,
            String settingKey,
            String settingField
    ) {
        // mergeSettingIfSetAndMap(settingsMap, settingField, settingKey, );
    }

    public <U, F, T extends StringConverter<T>> void mergeSettingIfSetAndMap(
            HashMap<String, String> settingsMap,
            U settingsField,
            String settingsKey,
            Function<T, F> map
    ) {
        // TODO
    }

    public void mergeMillisSetting(Duration field, String key){
        // TODO
    }

    public List<String> getMembersFromSettings(HashMap<String, String> settings) {
        //TODO
        return null;
    }

    public int getMaxLogSize() {
        return this.maxLogSize;
    }

    public String getStorageLocation(){
        return storageLocation;
    }

    public Duration getBlockPublishingDelay() {
        return blockPublishingDelay;
    }
}
