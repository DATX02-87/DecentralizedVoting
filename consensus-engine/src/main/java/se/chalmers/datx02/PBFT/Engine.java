package se.chalmers.datx02.PBFT;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sawtooth.sdk.protobuf.Consensus;
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


            } catch (InterruptedException | InvalidProtocolBufferException e) {
                logger.error("Main loop received exception");
            }
        }
    }

    private void handleUpdate(DriverUpdate update) throws InvalidProtocolBufferException {
        switch(update.getMessageType()){
            case CONSENSUS_NOTIFY_BLOCK_NEW:
                node.onBlockNew(ConsensusBlock.parseFrom((byte[]) update.getData()), pbft_state);
                break;
            case CONSENSUS_NOTIFY_BLOCK_VALID:
                node.onBlockValid((byte[]) update.getData(), pbft_state);
                break;
            case CONSENSUS_NOTIFY_BLOCK_INVALID:
                node.onBlockInvalid((byte[]) update.getData());
                break;
            case CONSENSUS_NOTIFY_BLOCK_COMMIT:
                node.onBlockCommit((byte[]) update.getData(), pbft_state);
                break;
            case CONSENSUS_NOTIFY_PEER_MESSAGE:
                // TODO: fix this
                break;
            case CONSENSUS_NOTIFY_ENGINE_DEACTIVATED:
                logger.error("Received shutdown; stopping PBFT");
                this.stop();
                break;
            case CONSENSUS_NOTIFY_PEER_CONNECTED:

                // TODO: fix this
                logger.info("Received PeerConnected with peer info: ");
                // node.onPeerConnected();
                break;
            case CONSENSUS_NOTIFY_PEER_DISCONNECTED:
                logger.info("Received PeerDisconnected for peer ID: ");
                break;
            case NETWORK_DISCONNECT:
                logger.error("Disconnected from validator; stopping PBFT");
                this.stop();
                break;
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
