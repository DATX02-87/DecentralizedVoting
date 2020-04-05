package se.chalmers.datx02.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import sawtooth.sdk.protobuf.Batch;
import sawtooth.sdk.protobuf.ClientBatchStatus;
import sawtooth.sdk.protobuf.ClientStateListResponse;
import sawtooth.sdk.signing.Signer;
import se.chalmers.datx02.api.rest_model.StateEntry;
import se.chalmers.datx02.api.rest_model.StateReponse;
import se.chalmers.datx02.model.JSONUtil;
import se.chalmers.datx02.model.Transaction;
import se.chalmers.datx02.model.state.GlobalState;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ValidatorService {
    private final String validatorURI;
    private final String stateAddress;
    private final Signer signer;

    private HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private JsonFactory JSON_FACTORY = new JacksonFactory();
    private HttpRequestFactory requestFactory;

    private static ValidatorService instance;

    private ValidatorService(String validatorURI, String stateAddress, Signer signer) {
        this.validatorURI = validatorURI;
        this.stateAddress = stateAddress;
        this.signer = signer;
        requestFactory
                = HTTP_TRANSPORT.createRequestFactory(
                (HttpRequest request) -> {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                });
    }

    public static void init(String validatorURI, String stateAddress, Signer signer) {
        instance = new ValidatorService(validatorURI, stateAddress, signer);
    }

    public static ValidatorService getInstance() {
        if (instance == null) {
            throw new RuntimeException("ValidatorService not instantiated");
        }
        return instance;
    }

    /**
     * Post a transaction to the validator
     * @param transaction the transaction to post
     * @return the batch id for ability to get the status using {@link #getStatus(String)}
     */
    public String postTransaction(Transaction transaction) {
        return postTransactions(Collections.singletonList(transaction));
    }

    /**
     * Post a list of transactions to the validator
     * @param transactions the transactions to post
     * @return the batch id for ability to get the status using {@link #getStatus(String)}
     */
    public String postTransactions(List<Transaction> transactions) {
        // TODO sign and batch together transactions

        // TODO post batches
        return null;
    }

    /**
     * Get the status of a batch id
     * @param batchId the batch id to get status for
     * @return the status of the batch
     */
    public ClientBatchStatus.Status getStatus(String batchId) {
        // TODO
        return null;
    }

    /**
     * Get the state from the validator
     * @return the current state on the blockchain
     */
    public GlobalState getState() throws IOException {
        // TODO
        StateEntry entry = getRequest("/state", StateReponse.class).getData().get(0);
        ObjectMapper om = new ObjectMapper();
        GlobalState globalState = JSONUtil.StringToGlobalState(entry.getData());
        return globalState;

    }

    private sawtooth.sdk.protobuf.Transaction buildTransaction(Transaction transaction) {
        // TODO
        return null;
    }
    private Batch buildBatch(List<sawtooth.sdk.protobuf.Transaction> transactions) {
        // TODO
        return null;
    }

    public <T> T getRequest(String path, Class<T> clazz) throws IOException {
        // TODO
        String uri = "";
        return requestFactory.buildGetRequest(new GenericUrl(uri))
                .execute()
                .parseAs(clazz);
    }
}
