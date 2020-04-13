package se.chalmers.datx02.testutils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.DockerComposeManager;
import com.palantir.docker.compose.configuration.DockerComposeFiles;
import com.palantir.docker.compose.configuration.ProjectName;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
import com.palantir.docker.compose.execution.DockerComposeExecutable;
import com.palantir.docker.compose.execution.DockerConfiguration;
import com.palantir.docker.compose.execution.DockerExecutable;
import com.palantir.docker.compose.execution.ImmutableDockerComposeExecutable;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.List;

public class SawtoothComposeExtension implements BeforeAllCallback, AfterAllCallback, BeforeAfterAllCallback {

    private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private JsonFactory JSON_FACTORY = new JacksonFactory();
    private HttpRequestFactory requestFactory;

    private final List<String> waitForInstances;

    private DockerComposeExtension docker;

    public SawtoothComposeExtension(List<String> waitForInstances, String composeFile) {
        this(waitForInstances, composeFile, null, null);
    }

    public SawtoothComposeExtension(List<String> waitForInstances, String composeFile, String projectName, DockerConfiguration dockerConfiguration) {
        this.waitForInstances = waitForInstances;
        DockerComposeExtension.Builder builder = DockerComposeExtension.builder()
                .file(composeFile)
                .waitingForServices(
                        waitForInstances,
                        target -> target.stream()
                                .map(Container::areAllPortsOpen)
                                .filter(SuccessOrFailure::failed)
                                .findAny().orElse(SuccessOrFailure.success()));
        builder.saveLogsTo("build/dockerLogs/dockerComposeRuleTest/" + projectName);
        if (projectName != null) {
            builder = builder.projectName(ProjectName.fromString(projectName));
        }
        if (dockerConfiguration != null) {
            builder = builder.dockerExecutable(DockerExecutable.builder()
                    .dockerConfiguration(dockerConfiguration)
                    .build());
            ImmutableDockerComposeExecutable.Builder composeExtBuilder = DockerComposeExecutable.builder()
                    .dockerComposeFiles(DockerComposeFiles.from(composeFile))
                    .dockerConfiguration(dockerConfiguration);
            if (projectName != null) {
                composeExtBuilder.projectName(ProjectName.fromString(projectName));
            }
            builder = builder.dockerComposeExecutable(composeExtBuilder.build());
        }
        docker = builder.build();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        docker.afterAll(context);
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        docker.beforeAll(context);
        requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                });
    }

    public DockerComposeManager getDocker() {
        return docker;
    }

    public String buildUri(String containerName, int port, String path) {
        Container container = docker.containers().container(containerName);
        DockerPort dockerPort = container.port(port);
        return String.format("http://%s:%s/%s", dockerPort.getIp(), dockerPort.getExternalPort(), path);
    }

    public String buildUri(String transport, String containerName, int port) {
        Container container = docker.containers().container(containerName);
        DockerPort dockerPort = container.port(port);
        return String.format("%s://%s:%s", transport, dockerPort.getIp(), dockerPort.getExternalPort());
    }

    public <T> T getRequest(String uri, Class<T> clazz) throws IOException {
        ExponentialBackOff exponentialBackOff = new ExponentialBackOff.Builder()
                .setInitialIntervalMillis(500)
                .setMaxElapsedTimeMillis(900000)
                .setMaxIntervalMillis(6000)
                .setMultiplier(1.5)
                .setRandomizationFactor(0.5)
                .build();
        return requestFactory.buildGetRequest(new GenericUrl(uri))
                .setUnsuccessfulResponseHandler(new HttpBackOffUnsuccessfulResponseHandler(exponentialBackOff))
                .execute()
                .parseAs(clazz);
    }


}
