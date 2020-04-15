package se.chalmers.datx02.pbft;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import se.chalmers.datx02.PBFT.Config;
import se.chalmers.datx02.PBFT.Engine;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.impl.ZmqDriver;
import se.chalmers.datx02.testutils.ConsensusIntegrationTestExtension;
import se.chalmers.datx02.testutils.Response;
import se.chalmers.datx02.testutils.SawtoothComposeExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag(value = "integration")
public class PbftProgressAltIntegrationTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RegisterExtension
    public static ConsensusIntegrationTestExtension integrationTestExtension = new ConsensusIntegrationTestExtension();

    private ConsensusIntegrationTestExtension.SawtoothWorkloadExecutor workloadExecutor;

    private ConsensusIntegrationTestExtension.SawtoothService service;

    private final Map<Thread, Driver> engines = new HashMap<>();

    @Test
    @Timeout(360)
    public void testProgress() throws Exception {
        startConsensus();
        SawtoothComposeExtension node = service.getExtension();
        String blocksUrl = node.buildUri("rest-api", 8008, "blocks");
        int numBlocks = 0;
        while (numBlocks <= 0) {
            System.out.println("waiting for blocks...");
            Response res = node.getRequest(blocksUrl, Response.class);
            numBlocks = res.getData().size();
        }
        System.out.println("Blockchain ready");
        workloadExecutor.start();

        while (numBlocks <= 55) {
            System.out.println("blocks: " + numBlocks);
            Response res = node.getRequest(blocksUrl, Response.class);
            numBlocks = res.getData().size();
            Thread.sleep(1000);
        }
        assertTrue(allChainsApproxEqual(), "All chains have approximately same length");
        workloadExecutor.stop();
        System.out.println("Stopping consensus engines");
        stopConsensusEngines();
        System.out.println("DONE with progress test");

    }

    private boolean allChainsApproxEqual() throws IOException {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        List<Integer> blockSizes = service.getComposeExtensions().parallelStream()
                .map(n -> {
                    try {
                        return n.getRequest(n.buildUri("rest-api", 8008, "blocks"), Response.class).getData().size();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        for (Integer blockSize : blockSizes) {
            if (blockSize > max) {
                max = blockSize;
            }
            if (blockSize < min) {
                min = blockSize;
            }
        }
        return max - min <= 3;
    }

    private void stopConsensusEngines() throws InterruptedException {
        for (Map.Entry<Thread, Driver> e : engines.entrySet()) {
            e.getValue().stop();
            e.getKey().join();
        }
    }

    private void startConsensus() {
        for (Map.Entry<Integer, String> idxValidatorUri : service.getValidatorUris().entrySet()) {
            int nodeIdx = idxValidatorUri.getKey();
            String validatorUri = idxValidatorUri.getValue();
            Config pbftConfig = new Config();
            Driver driver = new ZmqDriver(new Engine(pbftConfig));
            Thread thread = new Thread(() -> {
                Thread.currentThread().setName("engine-" + nodeIdx);
                MDC.put("context", "driver");
                driver.start(validatorUri);
            });
            thread.start();
            thread.setUncaughtExceptionHandler((t, e) -> {
                logger.error("Engine {} exited", nodeIdx, e);
                fail();
                try {
                    stopConsensusEngines();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });
            engines.put(thread, driver);
        }
    }
}
