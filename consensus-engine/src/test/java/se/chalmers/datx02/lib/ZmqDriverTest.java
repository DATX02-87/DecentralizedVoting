package se.chalmers.datx02.lib;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import sawtooth.sdk.protobuf.*;
import se.chalmers.datx02.lib.models.DriverUpdate;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ZmqDriverTest {
    private TestUtils.TestCommsHelper commsHelper;
    private MockEngine engine;
    private Driver driver;
    private String url;

    @BeforeEach
    void before() {
        commsHelper = new TestUtils.TestCommsHelper();
        commsHelper.start();
        url = commsHelper.getSocket().getLastEndpoint();
        engine = new MockEngine();
        driver = new ZmqDriver(engine);
    }

    @Test
    @Timeout(5)
    void testDriver() throws InterruptedException {
        Thread driverThread = new Thread(() -> driver.start(url));
        driverThread.start();

        byte[] response = ConsensusRegisterResponse.newBuilder()
                .setStatus(ConsensusRegisterResponse.Status.OK)
                .build().toByteArray();
        ConsensusRegisterRequest request = commsHelper.recvResp(response, Message.MessageType.CONSENSUS_REGISTER_RESPONSE, ConsensusRegisterRequest.parser());
        assertEquals("test-name", request.getName());
        assertEquals("test-version", request.getVersion());
        commsHelper.sendReqResp(ConsensusNotifyEngineActivated.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_ENGINE_ACTIVATED);
        commsHelper.sendReqResp(ConsensusNotifyPeerConnected.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_PEER_CONNECTED);
        commsHelper.sendReqResp(ConsensusNotifyPeerDisconnected.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_PEER_DISCONNECTED);
        commsHelper.sendReqResp(ConsensusNotifyPeerMessage.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_PEER_MESSAGE);
        commsHelper.sendReqResp(ConsensusNotifyBlockNew.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_BLOCK_NEW);
        commsHelper.sendReqResp(ConsensusNotifyBlockValid.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_BLOCK_VALID);
        commsHelper.sendReqResp(ConsensusNotifyBlockInvalid.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_BLOCK_INVALID);
        commsHelper.sendReqResp(ConsensusNotifyBlockCommit.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_BLOCK_COMMIT);
        commsHelper.sendReqResp(PingRequest.newBuilder().build().toByteArray(), Message.MessageType.PING_REQUEST);
        List<Message.MessageType> typesList = Arrays.asList(
                Message.MessageType.CONSENSUS_NOTIFY_PEER_CONNECTED,
                Message.MessageType.CONSENSUS_NOTIFY_PEER_DISCONNECTED,
                Message.MessageType.CONSENSUS_NOTIFY_PEER_MESSAGE,
                Message.MessageType.CONSENSUS_NOTIFY_BLOCK_NEW,
                Message.MessageType.CONSENSUS_NOTIFY_BLOCK_VALID,
                Message.MessageType.CONSENSUS_NOTIFY_BLOCK_INVALID,
                Message.MessageType.CONSENSUS_NOTIFY_BLOCK_COMMIT
        );
        List<Message.MessageType> engineTypes = engine.getUpdates().stream().map(DriverUpdate::getMessageType).collect(Collectors.toList());
        assertEquals(typesList, engineTypes);
        driver.stop();
        driverThread.join();
    }

    @Test
    @Timeout(5)
    void testStopEngine() throws InterruptedException {
        Thread driverThread = new Thread(() -> driver.start(url));
        driverThread.start();

        byte[] response = ConsensusRegisterResponse.newBuilder()
                .setStatus(ConsensusRegisterResponse.Status.OK)
                .build().toByteArray();
        ConsensusRegisterRequest request = commsHelper.recvResp(response, Message.MessageType.CONSENSUS_REGISTER_RESPONSE, ConsensusRegisterRequest.parser());
        assertEquals("test-name", request.getName());
        assertEquals("test-version", request.getVersion());

        byte[] exitMsg = ConsensusNotifyEngineDeactivated.newBuilder().build().toByteArray();
        commsHelper.sendReqResp(ConsensusNotifyEngineActivated.newBuilder().build().toByteArray(), Message.MessageType.CONSENSUS_NOTIFY_ENGINE_ACTIVATED);
        response = commsHelper.sendReqResp(exitMsg, Message.MessageType.CONSENSUS_NOTIFY_ENGINE_DEACTIVATED);
        // join the driver thread since it should now exit
        driverThread.join();
        assertEquals(1, 1, "Thread exited successfully");
    }
    @AfterEach
    void after() throws Exception {
        commsHelper.close();
    }

    private static class MockEngine implements Engine {
        private List<DriverUpdate> updates = new LinkedList<>();
        private AtomicBoolean exit = new AtomicBoolean(false);

        private List<DriverUpdate> getUpdates() {
            return updates;
        }

        @Override
        public void start(BlockingQueue<DriverUpdate> updates, Service service, StartupState startupState) {
            while(!exit.get()) {
                try {
                    DriverUpdate update = updates.poll(1, TimeUnit.SECONDS);
                    if ( update == null) {
                        throw new TimeoutException("timed out getting driver update");
                    }
                    this.updates.add(update);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    continue;
                }
            }
        }

        @Override
        public void stop() {
            exit.set(true);
        }

        @Override
        public String getVersion() {
            return "test-version";
        }

        @Override
        public String getName() {
            return "test-name";
        }

        @Override
        public Map<String, String> additionalProtocol() {
            return new HashMap<>();
        }
    }

}