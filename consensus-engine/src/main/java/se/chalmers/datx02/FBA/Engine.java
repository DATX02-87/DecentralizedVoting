package se.chalmers.datx02.FBA;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pbft.sdk.protobuf.PbftMessageInfo;
import sawtooth.sdk.protobuf.ConsensusBlock;
import se.chalmers.datx02.FBA.lib.Storage;
import se.chalmers.datx02.FBA.lib.exceptions.InternalError;
import se.chalmers.datx02.FBA.lib.exceptions.*;
import se.chalmers.datx02.FBA.lib.timing.Ticker;
import se.chalmers.datx02.FBA.message.ParsedMessage;
import se.chalmers.datx02.lib.models.DriverUpdate;
import se.chalmers.datx02.lib.models.PeerMessage;
import se.chalmers.datx02.lib.models.StartupState;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static se.chalmers.datx02.PBFT.lib.Storage.get_storage;

public class Engine implements se.chalmers.datx02.lib.Engine {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicBoolean exit = new AtomicBoolean(false);
    private BlockingQueue<DriverUpdate> updates;
    private StartupState startupState;
    private se.chalmers.datx02.lib.Service service;
    private Config config;
    private Node node;
    private State pbft_state;

    /**
     * Initializes a new engine
     * @param config specifies the config to be used
     */
    public Engine(Config config){
        this.config = config;
    }

    /**
     * Starts the engine
     * @param updates specifies the driver updates to be received
     * @param service specifies the service to be used
     * @param startupState specifies the start up state
     */
    @Override
    public void start(BlockingQueue<DriverUpdate> updates, se.chalmers.datx02.lib.Service service, StartupState startupState) {
        // Startup
        logger.info(startupState.toString());

        this.service = service;
        this.startupState = startupState;
        this.updates = updates;

        // Load settings
        this.config.loadSettings(this.startupState.getChainHead().getBlockId().toByteArray(), this.service);

        logger.info("PBFT config loaded: " + this.config.toString());

        // Get stored state
        try {
            pbft_state = (State) get_storage(this.config.getStorageLocation());
        }
        catch(Exception e){
            pbft_state = new State(
                    this.startupState.getLocalPeerInfo().getPeerId().toByteArray(),
                    this.startupState.getChainHead().getBlockNum(),
                    this.config
            );
        }

        // Load UNL
        try {
            this.config.loadUNL();
        } catch (InternalError e) {
            logger.error(String.valueOf(e));
            return;
        }

        // Set name if empty
        this.config.setName(pbft_state.getPeerId());

        logger.info("PBFT state created: " + pbft_state);

        // Init node
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

            } catch (InterruptedException | InvalidProtocolBufferException | InvalidMessage | SerializationError | ServiceError | InternalError e) {
                logger.error("Main loop received exception: " + e);
            }
        }

        // Save config when engine stops
        try {
            Storage.save_storage(this.config.getStorageLocation(), node.getState());
        } catch (IOException e) {
            logger.error(String.format("Failed to save state to storage on exit, due to: %s", e));
        } catch (StoredInMemory storedInMemory) {
            logger.info("Failed to save storage on exit because it uses memory for storage");
        }
    }

    /**
     * A helper function to handle different updates
     * @param update specifies update to be matched
     * @throws InvalidProtocolBufferException
     * @throws InvalidMessage
     * @throws SerializationError
     * @throws ServiceError
     * @throws InternalError
     */
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
                ParsedMessage parsedMessage = new ParsedMessage(peerMessage, node.getState().getPeerId());
                byte[] pbft_signer_id = parsedMessage.info().getSignerId().toByteArray();

                if(pbft_signer_id != verified_signer_id)
                    throw new InvalidMessage(String.format("Mismatch between PbftMessage's signer ID %s and PeerMessage's " +
                                    "signer ID %s of peer message: %s",
                            Arrays.toString(pbft_signer_id),
                            Arrays.toString(verified_signer_id),
                            parsedMessage.toString()));

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
                logger.info("Received PeerDisconnected for peer ID: " + update.getData());
                break;
            case NETWORK_DISCONNECT:
                logger.error("Disconnected from validator; stopping PBFT");
                this.stop();
                break;
            default:
                logger.error("Received undefined messagetype: " + update.getMessageType());
                break;
        }
    }

    /**
     * Stops engine
     */
    @Override
    public void stop() {
        this.exit.set(true);
    }

    /**
     * Engine version
     * @return returns engine version
     */
    @Override
    public String getVersion() {
        return "1.0";
    }

    /**
     * Engine name
     * @return returns engine name
     */
    @Override
    public String getName() {
        return "PBFT-java";
    }

    /**
     * Additional protocols
     * @return returns additional protocols
     */
    @Override
    public Map<String, String> additionalProtocol() {
        return Collections.emptyMap();
    }
}
