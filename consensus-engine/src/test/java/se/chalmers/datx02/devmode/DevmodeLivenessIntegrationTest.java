package se.chalmers.datx02.devmode;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.impl.ZmqDriver;
import se.chalmers.datx02.testutils.Block;
import se.chalmers.datx02.testutils.Response;
import se.chalmers.datx02.testutils.SawtoothComposeExtension;
import sun.jvm.hotspot.runtime.Threads;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag(value = "integration")
class DevmodeLivenessIntegrationTest {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int COMPONENT_PORT = 4004;
    public static final int VALIDATOR_PORT = 8800;
    public static final int CONSENSUS_PORT = 5005;
    public static final int BATCH_PER_BLOCK_MIN = 1;
    public static final int BATCH_PER_BLOCK_MAX = 100;
    public static final int BLOCKS_TO_REACH = 55;
    public static final int BLOCKS_TO_CHECK_CONSENSUS = 52;
    public static final int MIN_TOTAL_BATCHES = 100;
    public static final int REST_API_PORT = 8008;
    private static final List<Integer> INSTANCES = IntStream.range(0, 5).boxed().collect(Collectors.toList());
    private final Map<Thread, Driver> engines = new HashMap<>();


    @RegisterExtension
    public static SawtoothComposeExtension sawtoothComposeExtension = new SawtoothComposeExtension(
            INSTANCES.stream()
                    .map(i -> Arrays.asList("validator-" + i, "rest-api-" + i, "intkey-tp-" + i, "settings-tp-" + i))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList()),
            "src/test/resources/integration/test_devmode_engine_liveness.yaml"
    );

    @Test
    public void test() throws InterruptedException {
        startConsensusEngines();
        Set<Integer> nodesReached = new TreeSet<>();
        // test that the blockchain reaches a specified size
        while (nodesReached.size() < INSTANCES.size()) {
            for (Integer i : INSTANCES) {
                if (nodesReached.contains(i)) {
                    continue;
                }
                Block block;
                try {
                    block = getBlock(i);
                    if (block == null) {
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                // assert that the block has correct number of batches
                assertTrue(checkBlocksBatchCount(block, BATCH_PER_BLOCK_MIN, BATCH_PER_BLOCK_MAX), String.format("Instance %s har the wrong number of batches, block no. %s", i, block.getHeader().getBlockNum()));
                if (block.getHeader().getBlockNum() >= BLOCKS_TO_REACH) {
                    nodesReached.add(i);
                }
                logBlock(i, block);
            }
            Thread.sleep(5000);
        }
        List<List<Block>> chains = getChains();
        // test that all nodes are in consensus
        assertTrue(checkConsensus(chains, BLOCKS_TO_CHECK_CONSENSUS));
        // test that an acceptable number of batches has been committed
        assertTrue(checkMinBatches(chains.get(0), MIN_TOTAL_BATCHES));
        logger.info("Test done, now stopping docker compose");
        stopConsensusEngines();
    }

    private void stopConsensusEngines() throws InterruptedException {
        for (Map.Entry<Thread, Driver> e : engines.entrySet()) {
            e.getValue().stop();
            e.getKey().join();
        }

    }

    private void startConsensusEngines() {
        for (Integer instance : INSTANCES) {
            Driver driver = new ZmqDriver(new DevmodeEngine());
            String endpoint = sawtoothComposeExtension.buildUri("tcp", "validator-" + instance, 5005);
            Thread thread = new Thread(() -> {
                try {
                    Thread.currentThread().setName("engine-" + instance);
                    MDC.put("context", "driver");
                    driver.start(endpoint);
                } catch (Exception e) {
                    e.printStackTrace();
                    fail();
                    System.exit(1);
                }
            });
            thread.start();
            engines.put(thread, driver);
        }

    }

    private boolean checkMinBatches(List<Block> blocks, int minBatches) {
        return blocks.stream()
                .map(b -> b.getHeader().getBatchIds().size())
                .reduce(Integer::sum).orElse(0) >= minBatches;
    }

    private boolean checkConsensus(List<List<Block>> chains, int blockNum) {
        List<Block> blocksAtNum = chains.stream()
                .map(chain -> chain
                        .stream()
                        .filter(b -> b.getHeader().getBlockNum() == blockNum)
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Block not found in chain")))
                .collect(Collectors.toList());
        Block block0 = blocksAtNum.get(0);
        return blocksAtNum.parallelStream().allMatch(b -> b.getHeaderSignature().equals(block0.getHeaderSignature()));
    }

    private List<List<Block>> getChains() {
        return INSTANCES.stream()
                .map(this::getAllBlocks)
                .collect(Collectors.toList());
    }

    private void logBlock(Integer i, Block block) {
        List<String> batchIds = block.getHeader().getBatchIds()
                .parallelStream()
                .map(id -> id.substring(0, 6))
                .collect(Collectors.toList());
        logger.info(String.format(
                "Validator-%s has block %s: %s, batches (%s): %s",
                i,
                block.getHeader().getBlockNum(),
                block.getHeaderSignature().substring(0, 6) + "...",
                batchIds.size(),
                batchIds));
    }

    private boolean checkBlocksBatchCount(Block block, int batchPerBlockMin, int batchPerBlockMax) {
        int batchCount = block.getHeader().getBatchIds().size();
        return batchPerBlockMin <= batchCount && batchCount <= batchPerBlockMax;
    }


    private Block getBlock(int node) throws IOException {
        String address = sawtoothComposeExtension.buildUri("rest-api-" + node, REST_API_PORT, "blocks?count=1");
        Response response = sawtoothComposeExtension.getRequest(address, Response.class);
        return response.getData().get(0);
    }

    private List<Block> getAllBlocks(int node) {
        String address = sawtoothComposeExtension.buildUri("rest-api-" + node, REST_API_PORT, "blocks");
        try {
            return sawtoothComposeExtension.getRequest(address, Response.class).getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}