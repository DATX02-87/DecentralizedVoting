package se.chalmers.datx02.devmode;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import se.chalmers.datx02.testutils.Block;
import se.chalmers.datx02.testutils.Response;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag(value = "integration")
class DevmodeEngineIntegrationTest {
    private final static Logger LOGGER = Logger.getLogger(DevmodeEngineIntegrationTest.class.getName());
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();
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
    private static HttpRequestFactory requestFactory;


    @BeforeAll
    public static void before() {
        requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                });
    }

    @RegisterExtension
    public static DockerComposeExtension docker = DockerComposeExtension.builder()
            .file("src/test/resources/integration/test_devmode_engine_liveness.yaml")
            .waitingForServices(
                    INSTANCES.stream()
                            .map(i -> Arrays.asList("validator-" + i, "rest-api-" + i, "intkey-tp-" + i, "settings-tp-" + i))
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList()),
                    target -> target.stream()
                            .map(Container::areAllPortsOpen)
                            .filter(SuccessOrFailure::failed)
                            .findAny().orElse(SuccessOrFailure.success()))
            .saveLogsTo("logs/docker-logs/" + DevmodeEngineIntegrationTest.class.getSimpleName())
            .build();

    @Test
    public void test() throws InterruptedException {
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
                assertTrue(checkBlocksBatchCount(block, BATCH_PER_BLOCK_MIN, BATCH_PER_BLOCK_MAX));
                if (block.getHeader().getBlockNum() >= BLOCKS_TO_REACH) {
                    nodesReached.add(i);
                }
                logBlock(i, block);
            }
            Thread.sleep(1000);
        }
        List<List<Block>> chains = getChains();
        // test that all nodes are in consensus
        assertTrue(checkConsensus(chains, BLOCKS_TO_CHECK_CONSENSUS));
        // test that an acceptable number of batches has been committed
        assertTrue(checkMinBatches(chains.get(0), MIN_TOTAL_BATCHES));
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
        LOGGER.info(String.format(
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
        String address = buildUri(node, "blocks?count=1");
        Response response = getRequest(address);
        return response.getData().get(0);
    }

    private List<Block> getAllBlocks(int node) {
        String address = buildUri(node, "blocks");
        try {
            return getRequest(address).getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildUri(int node, String path) {
        Container container = docker.containers().container("rest-api-" + node);
        DockerPort port = container.port(REST_API_PORT);
        return String.format("http://%s:%s/%s", port.getIp(), port.getExternalPort(), path);
    }

    private Response getRequest(String uri) throws IOException {
        return requestFactory.buildGetRequest(new GenericUrl(uri)).execute()
                .parseAs(Response.class);
    }






}