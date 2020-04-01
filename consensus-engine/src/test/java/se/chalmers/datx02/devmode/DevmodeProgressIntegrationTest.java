package se.chalmers.datx02.devmode;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.Engine;
import se.chalmers.datx02.lib.impl.ZmqDriver;
import se.chalmers.datx02.testutils.Block;
import se.chalmers.datx02.testutils.Response;
import se.chalmers.datx02.testutils.SawtoothComposeExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag(value = "integration")
public class DevmodeProgressIntegrationTest {
    final Logger logger = LoggerFactory.getLogger(getClass());
    public static final int BATCH_PER_BLOCK_MIN = 1;
    public static final int BATCH_PER_BLOCK_MAX = 100;
    public static final int BLOCKS_TO_REACH = 10;
    public static final int REST_API_PORT = 8008;

    @RegisterExtension
    public static SawtoothComposeExtension sawtoothComposeExtension = new SawtoothComposeExtension(
            Arrays.asList("validator", "rest-api", "intkey-tp", "settings-tp","intkey-workload"),
            "src/test/resources/integration/devmode/test_devmode_simple.yaml");

    @Test
    public void testProgress() throws InterruptedException {
        Driver driver = new ZmqDriver(new DevmodeEngine());
        String consensusUri = buildConsensusUri();
        Thread driverThread = new Thread(() -> driver.start(consensusUri));
        driverThread.start();

        while(getHeight() < BLOCKS_TO_REACH) {
            Block lastBlock = getLastBlock();
            logBlock(lastBlock);
            Thread.sleep(3000);
        }
        // check that there is the correct amount of batches in each block
        List<Block> chain = getChain();
        assertTrue(checkChainBatchCount(chain, BATCH_PER_BLOCK_MIN, BATCH_PER_BLOCK_MAX));
        logger.info("Progress test done, shutting down");
        driver.stop();
        driverThread.join();

    }

    private String buildConsensusUri() {
        return sawtoothComposeExtension.buildUri("tcp", "validator", 5005);
    }

    private boolean checkChainBatchCount(List<Block> chain, int batchPerBlockMin, int batchPerBlockMax) {
        return chain.parallelStream()
                .allMatch(b -> checkBlocksBatchCount(b, batchPerBlockMin, batchPerBlockMax));
    }

    private boolean checkBlocksBatchCount(Block block, int batchPerBlockMin, int batchPerBlockMax) {
        int batchCount = block.getHeader().getBatchIds().size();
        return batchPerBlockMin <= batchCount && batchCount <= batchPerBlockMax;
    }

    private Block getLastBlock() {
        String address = sawtoothComposeExtension.buildUri("rest-api", REST_API_PORT, "blocks?count=1");
        try {
            return sawtoothComposeExtension
                    .getRequest(address, Response.class)
                    .getData().get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int getHeight() {
        return getChain().size();
    }

    private List<Block> getChain() {
        String address = sawtoothComposeExtension.buildUri("rest-api", REST_API_PORT, "blocks");
        try {
            return sawtoothComposeExtension
                    .getRequest(address, Response.class)
                    .getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void logBlock(Block block) {
        List<String> batchIds = block.getHeader().getBatchIds()
                .parallelStream()
                .map(id -> id.substring(0, 6))
                .collect(Collectors.toList());
        logger.info(String.format(
                "Block %s: %s, batches (%s): %s",
                block.getHeader().getBlockNum(),
                block.getHeaderSignature().substring(0, 6) + "...",
                batchIds.size(),
                batchIds));
    }
}
