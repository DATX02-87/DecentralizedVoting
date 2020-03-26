package se.chalmers.datx02.PBFT;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.ConsensusBlock;
import sawtooth.sdk.protobuf.ConsensusNotifyBlockCommit;
import sawtooth.sdk.protobuf.ConsensusNotifyPeerMessage;
import se.chalmers.datx02.PBFT.lib.Ticker;
import se.chalmers.datx02.lib.exceptions.ReceiveErrorException;
import se.chalmers.datx02.lib.exceptions.UnknownBlockException;
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
    private se.chalmers.datx02.lib.Service service;
    private Config config;

    // TODO: Implementation


    @Override
    public void start(BlockingQueue<DriverUpdate> updates, se.chalmers.datx02.lib.Service service, StartupState startupState) {
        // Startup
        logger.info(startupState.toString());

        this.service = service;
        this.startupState = startupState;
        this.updates = updates;

        // Load settings
        this.config = new Config();
        try {
            this.config.loadSettings(this.startupState.getChainHead().getBlockId().toByteArray(), this.service);
        } catch (UnknownBlockException | ReceiveErrorException | InterruptedException e) {
            logger.warn("Failed to load settings from config");
            return;
        }

        logger.info("PBFT config loaded: " + this.config.toString());

        State pbft_state = null;
        // PBFT state
        /*
        // TODO: Fix RAII storage
        State pbft_state = storage(this.config.getStorageLocation(),
                new State(
                        this.startupState.getLocalPeerInfo().getPeerId().toByteArray(),
                        this.startupState.getChainHead().getBlockNum(),
                        this.config
                ));


        logger.info("PBFT state created: " + pbft_state.read());
        */


        Ticker block_publishing_ticker = new Ticker(this.config.getBlockPublishingDelay());

        Node node = new Node(
                this.config,
                this.startupState.getChainHead(),
                this.startupState.getPeers(),
                this.service,
                pbft_state
        );

    }

    private void engineLoop(){

        /*
         * 1. Wait for an incoming message
         * 2. Check for exit
         * 3. Handle the message
         * 4. Check for publishing
         */
        while (!exit.get()) {
        }
    }

    private void handleUpdate(){

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
