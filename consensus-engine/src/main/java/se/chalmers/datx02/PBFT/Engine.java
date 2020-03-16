package se.chalmers.datx02.PBFT;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusNotifyBlockCommit;
import sawtooth.sdk.protobuf.ConsensusNotifyPeerMessage;
import se.chalmers.datx02.lib.models.DriverUpdate;
import se.chalmers.datx02.lib.models.StartupState;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Engine implements se.chalmers.datx02.lib.Engine {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicBoolean exit = new AtomicBoolean(false);
    private BlockingQueue<DriverUpdate> updates;
    private StartupState startupState;
    private Service service;
    private Config config;

    // TODO: Implementation


    @Override
    public void start(BlockingQueue<DriverUpdate> updates, se.chalmers.datx02.lib.Service service, StartupState startupState) {
        this.service = new Service(service);
        this.startupState = startupState;
        this.updates = updates;


        this.config = new Config();

        try {
            engineLoop();
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException("Could not parse protocol buffer", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Engine update interrupted", e);
        }
    }

    private void engineLoop() throws InvalidProtocolBufferException, InterruptedException {

        /*
         * 1. Wait for an incoming message
         * 2. Check for exit
         * 3. Handle the message
         * 4. Check for publishing
         */
        while (!exit.get()) {
        }
    }

    public static boolean checkConsensus(ConsensusBlock block) {
        return Arrays.equals(block.getPayload().toByteArray(), createConsensus(block.getSummary().toByteArray()));
    }

    public static byte[] createConsensus(byte[] blockSummary) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write("PBFT".getBytes());
            baos.write(blockSummary);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Could not write bytearrayoutputstream");
        }
    }

    @Override
    public void stop() {
        this.exit.set(true);
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getName() {
        return "PBFT-java";
    }

    @Override
    public Map<String, String> additionalProtocol() {
        return Collections.emptyMap();
    }
}
