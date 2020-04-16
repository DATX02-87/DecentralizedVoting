package se.chalmers.datx02.testutils;

import com.palantir.docker.compose.DockerComposeManager;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsensusIntegrationTestExtension implements TestInstancePostProcessor, BeforeAllCallback, AfterAllCallback {
    private static final String ISOLATION_ID = "latest";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final DockerExecutor DOCKER_EXECUTOR = new DockerExecutor("ISOLATION_ID", ISOLATION_ID);
    private final int numNodes;
    private SawtoothComposeExtension adminNode;
    private MultipleExtension<SawtoothComposeExtension> nodes;
    private SawtoothWorkloadExecutor workloadExecutor = null;

    public ConsensusIntegrationTestExtension() {
        this(4, "src/test/resources/integration/generic/node.yaml");
    }

    public ConsensusIntegrationTestExtension(int numNodes) {
        this(numNodes, "src/test/resources/integration/generic/node.yaml");
    }

    public ConsensusIntegrationTestExtension(int numNodes, String nodeComposeFile) {
        this.numNodes = numNodes;
        // add regular nodes
        List<SawtoothComposeExtension> nodes = IntStream.range(0, numNodes - 1).boxed()
                .map(i -> new SawtoothComposeExtension(
                        Collections.emptyList(),
                        nodeComposeFile,
                        ISOLATION_ID + i,
                        DOCKER_EXECUTOR.getConfiguration())).collect(Collectors.toList());
        // add genesis node
        nodes.add(new SawtoothComposeExtension(
                Collections.emptyList(),
                nodeComposeFile,
                ISOLATION_ID + (numNodes - 1),
                new DockerExecutor("ISOLATION_ID", ISOLATION_ID, "GENESIS", "1").getConfiguration()
        ));
        this.nodes = new MultipleExtension<>(nodes);
        this.adminNode = new SawtoothComposeExtension(Collections.emptyList(), "src/test/resources/integration/generic/admin.yaml");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        DOCKER_EXECUTOR.performCommandWithException("network", "create", "sawtooth_validators_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("network", "create", "sawtooth_rest_apis_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("volume", "create", "--name=sawtooth_shared_data_" + ISOLATION_ID);

        adminNode.beforeAll(context);
        nodes.beforeAll(context);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (this.workloadExecutor != null && this.workloadExecutor.isRunning()) {
            this.workloadExecutor.stop();
        }
        adminNode.afterAll(context);
        nodes.afterAll(context);

        DOCKER_EXECUTOR.performCommandWithException("network", "rm", "sawtooth_validators_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("network", "rm", "sawtooth_rest_apis_" + ISOLATION_ID);
        DOCKER_EXECUTOR.performCommandWithException("volume", "rm", "sawtooth_shared_data_" + ISOLATION_ID);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        // inject workload executor & engine helper
        for (Field field : testInstance.getClass().getDeclaredFields()) {
            if (field.getType() == SawtoothWorkloadExecutor.class) {
                boolean prevAccessible = field.isAccessible();
                field.setAccessible(true);
                if (this.workloadExecutor == null) {
                    this.workloadExecutor = new SawtoothWorkloadExecutor();
                }
                field.set(testInstance, this.workloadExecutor);
                field.setAccessible(prevAccessible);
            }
            if (field.getType() == SawtoothService.class) {
                boolean prevAccessible = field.isAccessible();
                field.setAccessible(true);
                field.set(testInstance, getService());
                field.setAccessible(prevAccessible);
            }
        }


    }

    private SawtoothService getService() {
        List<SawtoothComposeExtension> extensions = IntStream.range(0, this.numNodes).boxed()
                .map(i -> nodes.getExtension(i)).collect(Collectors.toList());
        List<String> validatorUris = extensions.parallelStream()
                .map(s -> s.buildUri("tcp", "validator", 5050))
                .collect(Collectors.toList());
        return new SawtoothService(validatorUris, extensions);
    }

    public static class SawtoothWorkloadExecutor {
        private DockerComposeManager workload = new SawtoothComposeExtension(
                Collections.emptyList(),
                "src/test/resources/integration/generic/workload.yaml",
                "workload",
                new DockerExecutor("ISOLATION_ID", ISOLATION_ID, "RATE", "1").getConfiguration()
        ).getDocker();
        private boolean running = false;

        public void start() {
            try {
                this.workload.before();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.running = true;
        }

        public void stop() {
            this.workload.after();
            this.running = false;
        }
        protected boolean isRunning() {
            return this.running;
        }
    }

    public static class SawtoothService {
        private final Map<Integer, String> validatorUris;
        private final List<SawtoothComposeExtension> composeExtensions;

        public SawtoothService(List<String> validatorUris, List<SawtoothComposeExtension> composeExtensions) {
            this.composeExtensions = composeExtensions;
            this.validatorUris = new HashMap<>();
            int i = 0;
            for (String uri : validatorUris) {
                this.validatorUris.put(i++, uri);
            }
        }
        public Map<Integer, String> getValidatorUris() {
            return validatorUris;
        }

        public List<SawtoothComposeExtension> getComposeExtensions() {
            return composeExtensions;
        }

        public SawtoothComposeExtension getExtension() {
            return composeExtensions.get(new Random().nextInt(composeExtensions.size()));
        }
    }
}
