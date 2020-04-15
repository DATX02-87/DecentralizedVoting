package se.chalmers.datx02.pbft;

import com.palantir.docker.compose.DockerComposeManager;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.execution.DockerComposeExecutable;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import se.chalmers.datx02.PBFT.Config;
import se.chalmers.datx02.PBFT.Engine;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.impl.ZmqDriver;
import se.chalmers.datx02.testutils.DockerExecutor;
import se.chalmers.datx02.testutils.MultipleExtension;
import se.chalmers.datx02.testutils.Response;
import se.chalmers.datx02.testutils.SawtoothComposeExtension;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Tag(value = "integration")
public class PbftProgressIntegrationTest {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String ISOLATION_ID = "latest";

    private static final DockerExecutor DOCKER_EXECUTOR = new DockerExecutor("ISOLATION_ID", ISOLATION_ID);

    private final Map<Thread, Driver> engines = new HashMap<>();

    @Order(10)
    @RegisterExtension
    public static BeforeAllCallback setup = context -> {
        DOCKER_EXECUTOR.performCommandWithException("network", "create", "pbft_validators_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("network", "create", "pbft_rest_apis_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("volume", "create", "--name=pbft_shared_data_" + ISOLATION_ID);
    };

    @Order(10)
    @RegisterExtension
    public static AfterAllCallback teardown = context -> {
        DOCKER_EXECUTOR.performCommandWithException("network", "rm", "pbft_validators_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("network", "rm", "pbft_rest_apis_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("volume", "rm", "pbft_shared_data_" + ISOLATION_ID);
    };

    @Order(20)
    @RegisterExtension
    public static MultipleExtension<SawtoothComposeExtension> nodes = new MultipleExtension<>(Arrays.asList(
            new SawtoothComposeExtension(
                    Collections.emptyList(),
//                    Collections.singletonList("admin"),
                    "src/test/resources/integration/pbft/admin.yaml"
            ),
            generateNodeExtension("alpha"),
            generateNodeExtension("beta"),
            generateNodeExtension("gamma"),
            generateGenesisNodeExtension("delta")
    ));

    @Test
    @Timeout(value = 300)
    public void testProgress() throws Exception {

        System.out.println("Starting consensus engines");
        startConsensus(Arrays.asList(1, 2, 3, 4));

        // wait until first block exists
        SawtoothComposeExtension node = nodes.getExtension(1);
        String blocksUrl = node.buildUri("rest-api", 8008, "blocks");
        int numBlocks = 0;
        while (numBlocks <= 0) {
            System.out.println("waiting for blocks...");
            Response res = node.getRequest(blocksUrl, Response.class);
            numBlocks = res.getData().size();
        }
        System.out.println("Blockchain ready");
        // start workload
        DockerComposeManager workload = new DockerComposeManager.Builder()
                .file("src/test/resources/integration/pbft/workload.yaml")
                .dockerComposeExecutable(DockerComposeExecutable.builder()
                        .dockerConfiguration(new DockerExecutor("ISOLATION_ID", ISOLATION_ID, "RATE", "1").getConfiguration())
                        .dockerComposeFiles(DockerComposeFiles.from("src/test/resources/integration/pbft/workload.yaml"))
                        .build())
                .saveLogsTo("build/dockerLogs/dockerComposeRuleTest/workload")
                .build();
        workload.before();
        // wait until 55 blocks has been reached

        while (numBlocks <= 55) {
            System.out.println("blocks: " + numBlocks);
            Response res = node.getRequest(blocksUrl, Response.class);
            numBlocks = res.getData().size();
            Thread.sleep(1000);
        }
        // get the chain
        assertTrue(allChainsApproxEqual(), "All chains have approximately same length");

        workload.after();
        System.out.println("Stopping consensus engines");
        stopConsensusEngines();
        System.out.println("DONE with progress test");
    }

    private boolean allChainsApproxEqual() throws IOException {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        List<Integer> blockSizes = IntStream.range(1, 5).boxed()
                .map(i -> nodes.getExtension(i))
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

    private void startConsensus(List<Integer> extensionIdxs) {
        for (Integer extensionIdx : extensionIdxs) {
            SawtoothComposeExtension sawtooth = nodes.getExtension(extensionIdx);
            String validatorUri = sawtooth.buildUri("tcp", "validator", 5050);
            Config pbftConfig = new Config();
            Driver driver = new ZmqDriver(new Engine(pbftConfig));
            Thread thread = new Thread(() -> {
                Thread.currentThread().setName("engine-" + extensionIdx);
                MDC.put("context", "driver");
                driver.start(validatorUri);
            });
            thread.start();
            thread.setUncaughtExceptionHandler((t, e) -> {
                logger.error("Engine {} exited", extensionIdx, e);
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

    private static SawtoothComposeExtension generateNodeExtension(String suffix) {
        return new SawtoothComposeExtension(
//                Arrays.asList("validator", "rest-api", "intkey-tp-python", "settings-tp"),
                Collections.emptyList(),
                "src/test/resources/integration/pbft/node.yaml",
                ISOLATION_ID + suffix,
                DOCKER_EXECUTOR.getConfiguration()
        );
    }

    private static SawtoothComposeExtension generateGenesisNodeExtension(String suffix) {
        return new SawtoothComposeExtension(
//                Arrays.asList("validator", "rest-api", "intkey-tp-python", "settings-tp"),
                Collections.emptyList(),
                "src/test/resources/integration/pbft/node.yaml",
                ISOLATION_ID + suffix,
                new DockerExecutor("ISOLATION_ID", ISOLATION_ID, "GENESIS", "1").getConfiguration()
        );
    }


}
