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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.fail;

@Tag(value = "integration")
class DevmodeEngineIntegrationTest {
    private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final int COMPONENT_PORT = 4004;
    public static final int VALIDATOR_PORT = 8800;
    public static final int CONSENSUS_PORT = 5005;
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
            .build();

    @Test
    public void test() {
        System.out.println("INTEGRATION TEST");
        while (true) {
            Block block = null;
            try {
                block = getBlock(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (block == null) {
                continue;
            }

            System.out.println(block);
            break;
        }

//        fail();
    }


    private Block getBlock(int node) throws IOException {
        Container container = docker.containers().container("rest-api-" + node);
        DockerPort port = container.port(REST_API_PORT);
        String address = String.format("http://%s:%s/blocks?count=1", port.getIp(), port.getExternalPort());
        Response response = getRequest(address);
        return response.getData().get(0);
    }

    private Response getRequest(String uri) throws IOException {
        return requestFactory.buildGetRequest(new GenericUrl(uri)).execute()
                .parseAs(Response.class);
    }

    public static class Response extends GenericJson {
        @Key
        private String head;
        @Key
        private String link;
        @Key
        private List<Block> data;

        public String getHead() {
            return head;
        }

        public String getLink() {
            return link;
        }

        public List<Block> getData() {
            return data;
        }
    }

    public static class Block extends GenericJson {
        @Key(value = "header_signature")
        private String headerSignature;

        @Key
        private Header header;

        public String getHeaderSignature() {
            return headerSignature;
        }

        public Header getHeader() {
            return header;
        }
    }

    public static class Header extends GenericJson {
        @Key(value = "batch_ids")
        private List<String> batchIds;

        @Key(value = "block_num")
        private long blockNum;

        @Key(value = "previous_block_id")
        private String previousBlockId;

        @Key(value = "signer_public_key")
        private String signerPublicKey;

        @Key(value = "state_root_hash")
        private String stateRootHash;

        public List<String> getBatchIds() {
            return batchIds;
        }

        public long getBlockNum() {
            return blockNum;
        }

        public String getPreviousBlockId() {
            return previousBlockId;
        }

        public String getSignerPublicKey() {
            return signerPublicKey;
        }

        public String getStateRootHash() {
            return stateRootHash;
        }
    }


}