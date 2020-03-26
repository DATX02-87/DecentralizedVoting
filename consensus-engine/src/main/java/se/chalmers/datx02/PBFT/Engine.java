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
    private Node node;
    private State pbft_state;

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

        this.pbft_state = null;
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

        this.node = new Node(
                this.config,
                this.startupState.getChainHead(),
                this.startupState.getPeers(),
                this.service,
                pbft_state
        );

        node.startIdleTimeout(pbft_state);


        // Main loop
        DriverUpdate update;
        ConsensusBlock block;
        while (!exit.get()) {
            try {
                update = updates.poll(10, TimeUnit.MILLISECONDS);


                handleUpdate(update);

                // Try to publish if delay has passed
                if(block_publishing_ticker.Tick())
                    node.tryPublish(pbft_state);

                if(node.checkIdleTimeoutExpired(pbft_state)){
                    logger.warn("Idle timeout expired; proposing view change");
                    node.startViewChange(pbft_state.getView() + 1, pbft_state);
                }

                if(node.checkCommitTimeoutExpired(pbft_state)){
                    logger.warn("Commit timeout expired; proposing view change");
                    node.startViewChange(pbft_state.getView() + 1, pbft_state);
                }

                if(pbft_state.getMode() == State.Mode.ViewChanging){
                    if(node.checkViewChangeTimeoutExpired(pbft_state)){
                        long newView = pbft_state.getMode().viewChanging + 1;
                        logger.warn("View change timeout expired; proposing view change for view" + newView);

                        node.startViewChange(newView, pbft_state);
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpdate(DriverUpdate update){
        // TODO: fix this
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
