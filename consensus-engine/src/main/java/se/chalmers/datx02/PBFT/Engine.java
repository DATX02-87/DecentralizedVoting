package se.chalmers.datx02.PBFT;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.PbftMessageInfo;
import sawtooth.sdk.protobuf.ConsensusBlock;
import se.chalmers.datx02.PBFT.lib.exceptions.InternalError;
import se.chalmers.datx02.PBFT.lib.exceptions.ServiceError;
import se.chalmers.datx02.PBFT.lib.timing.Ticker;
import se.chalmers.datx02.PBFT.lib.exceptions.InvalidMessage;
import se.chalmers.datx02.PBFT.lib.exceptions.SerializationError;
import se.chalmers.datx02.PBFT.message.ParsedMessage;
import se.chalmers.datx02.lib.models.DriverUpdate;
import se.chalmers.datx02.lib.models.PeerMessage;
import se.chalmers.datx02.lib.models.StartupState;


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

    public Engine(Config config){
        this.config = config;
    }

    @Override
    public void start(BlockingQueue<DriverUpdate> updates, se.chalmers.datx02.lib.Service service, StartupState startupState) {
        // Startup
        logger.info(startupState.toString());

        this.service = service;
        this.startupState = startupState;
        this.updates = updates;

        // Load settings
        try {
            this.config.loadSettings(this.startupState.getChainHead().getBlockId().toByteArray(), this.service);
        } catch (InterruptedException e) {
            logger.warn("Failed to load settings from config");
            return;
        }

        logger.info("PBFT config loaded: " + this.config.toString());

        this.pbft_state = null;
        // PBFT state
        /*
        // TODO: Fix RAII storage (FIX CONFIG FILE FOR THIS)
        State pbft_state = get_storage(this.config.getStorageLocation(),
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

        node.startIdleTimeout();


        // Main loop
        DriverUpdate update;
        while (!exit.get()) {
            try {
                update = updates.poll(10, TimeUnit.MILLISECONDS);

                handleUpdate(update);

                // Try to publish if delay has passed
                if(block_publishing_ticker.Tick())
                    node.tryPublish();

                if(node.checkIdleTimeoutExpired()){
                    logger.warn("Idle timeout expired; proposing view change");
                    node.startViewChange(pbft_state.getView() + 1);
                }

                if(node.checkCommitTimeoutExpired()){
                    logger.warn("Commit timeout expired; proposing view change");
                    node.startViewChange(pbft_state.getView() + 1);
                }

                if(pbft_state.getMode() == State.Mode.ViewChanging){
                    if(node.checkViewChangeTimeoutExpired()){
                        long newView = pbft_state.getMode().getViewChanging() + 1;
                        logger.warn("View change timeout expired; proposing view change for view" + newView);

                        node.startViewChange(newView);
                    }
                }

            } catch (InterruptedException | InvalidProtocolBufferException | InvalidMessage | SerializationError | ServiceError | InternalError e) {
                logger.error("Main loop received exception: " + e);
            }
        }
    }

    private void handleUpdate(DriverUpdate update) throws InvalidProtocolBufferException, InvalidMessage, SerializationError, ServiceError, InternalError {
        switch(update.getMessageType()){
            case CONSENSUS_NOTIFY_BLOCK_NEW:
                node.onBlockNew(ConsensusBlock.parseFrom((byte[]) update.getData()));
                break;
            case CONSENSUS_NOTIFY_BLOCK_VALID:
                node.onBlockValid((byte[]) update.getData());
                break;
            case CONSENSUS_NOTIFY_BLOCK_INVALID:
                node.onBlockInvalid((byte[]) update.getData());
                break;
            case CONSENSUS_NOTIFY_BLOCK_COMMIT:
                node.onBlockCommit((byte[]) update.getData());
                break;
            case CONSENSUS_NOTIFY_PEER_MESSAGE:
                PeerMessage peerMessage = (PeerMessage) update.getData();

                byte[] verified_signer_id = peerMessage.getHeader().getSignerId().toByteArray();
                ParsedMessage parsedMessage = new ParsedMessage(peerMessage, pbft_state.getPeerId());
                byte[] pbft_signer_id = parsedMessage.info().getSignerId().toByteArray();

                if(pbft_signer_id != verified_signer_id)
                    throw new InvalidMessage("Mismatch between PbftMessage's signer ID " + pbft_signer_id + " and PeerMessage's signer ID " +
                            "" + verified_signer_id + " of peer message: " + parsedMessage.toString());


                node.onPeerMessage(parsedMessage);

                break;
            case CONSENSUS_NOTIFY_ENGINE_DEACTIVATED:
                logger.info("Received shutdown; stopping PBFT");
                this.stop();
                break;
            case CONSENSUS_NOTIFY_PEER_CONNECTED:
                logger.info("Received PeerConnected with peer info: ");
                node.onPeerConnected(((PbftMessageInfo) update.getData()).getSignerId().toByteArray());
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
