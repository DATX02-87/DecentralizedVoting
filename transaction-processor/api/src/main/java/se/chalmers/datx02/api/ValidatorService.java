package se.chalmers.datx02.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.protobuf.ByteString;
import sawtooth.sdk.protobuf.Batch;
import sawtooth.sdk.protobuf.BatchHeader;
import sawtooth.sdk.protobuf.BatchList;
import sawtooth.sdk.protobuf.TransactionHeader;
import sawtooth.sdk.signing.Signer;
import se.chalmers.datx02.api.model.validator.BatchListResponse;
import se.chalmers.datx02.api.model.validator.BatchStatus;
import se.chalmers.datx02.api.model.validator.BatchStatusResponse;
import se.chalmers.datx02.api.model.validator.StateReponse;
import se.chalmers.datx02.model.Adressing;
import se.chalmers.datx02.model.DataUtil;
import se.chalmers.datx02.model.Transaction;
import se.chalmers.datx02.model.TransactionPayload;
import se.chalmers.datx02.model.state.GlobalState;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public String postTransaction(Transaction transaction) throws IOException {
        return postTransactions(Collections.singletonList(transaction));
    }

    /**
     * Post a list of transactions to the validator
     * @param transactions the transactions to post
     * @return the batch id for ability to get the status using {@link #getStatus(String)}
     */
    public String postTransactions(List<Transaction> transactions) throws IOException {
        List<sawtooth.sdk.protobuf.Transaction> sawtoothTransactions = transactions.stream().map(this::buildTransaction).collect(Collectors.toList());
        Batch batch = buildBatch(sawtoothTransactions);
        byte[] batchList = BatchList.newBuilder()
                .addBatches(batch)
                .build()
                .toByteArray();
        return requestFactory.buildPostRequest(
                new GenericUrl(validatorURI + "/batches"),
                new ByteArrayContent("application/octet-stream", batchList)
        ).execute().parseAs(BatchListResponse.class).getLink();
    }

    /**
     * Get the status of a batch id
     * @param batchId the batch id to get status for
     * @return the status of the batch
     */
    public BatchStatus getStatus(String batchId) throws IOException {
        BatchStatusResponse response = getRequest("/batch_statuses?id=" + batchId, BatchStatusResponse.class);
        return response.getData().stream()
                .filter(bs -> bs.getId().equals(batchId))
                .findAny().orElseThrow(() -> new RuntimeException("No status for the batch supplied"));
    }

    /**
     * Get the state from the validator
     * @return the current state on the blockchain
     */
    public GlobalState getState() throws IOException {
        StateReponse state = getRequest("/state?address=" + stateAddress, StateReponse.class);
        if (state.getData().size() == 0) {
            // No data, return identity
            return new GlobalState();
        }
        String base64 = state.getData().get(0).getData();
        return DataUtil.StringToGlobalState(base64);
    }

    public byte[] buildTransactionHeader(String publicKey, TransactionPayload payload) {
        String serializedPayload = DataUtil.TransactionPayloadToString(payload);
        String hashedPayload = DataUtil.hash(serializedPayload);
        TransactionHeader transactionHeader = TransactionHeader.newBuilder()
                .setSignerPublicKey(publicKey)
                .setFamilyName(Adressing.FAMILY_NAME)
                .setFamilyVersion(Adressing.FAMILY_VERSION)
                .addInputs(stateAddress)
                .addInputs(stateAddress)
                .setPayloadSha512(hashedPayload)
                .setBatcherPublicKey(signer.getPublicKey().hex())
                .setNonce(UUID.randomUUID().toString())
                .build();
        return transactionHeader.toByteArray();

    }

    private sawtooth.sdk.protobuf.Transaction buildTransaction(Transaction transaction) {
        // TODO
        String payload = "";
        String hashedPayload = "";
        TransactionHeader transactionHeader = TransactionHeader.newBuilder()
                .setSignerPublicKey(signer.getPublicKey().hex())
                .setFamilyName(Adressing.FAMILY_NAME)
                .setFamilyVersion(Adressing.FAMILY_VERSION)
                .addInputs(stateAddress)
                .addInputs(stateAddress)
                .setPayloadSha512(hashedPayload)
                .setBatcherPublicKey(signer.getPublicKey().hex())
                .setNonce(UUID.randomUUID().toString())
                .build();
        String signature = signer.sign(transactionHeader.toByteArray());
        try {
            return sawtooth.sdk.protobuf.Transaction.newBuilder()
                    .setHeader(transactionHeader.toByteString())
                    .setPayload(ByteString.copyFrom(payload, "UTF-8"))
                    .setHeaderSignature(signature)
                    .build();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    private Batch buildBatch(List<sawtooth.sdk.protobuf.Transaction> transactions) {
        BatchHeader batchHeader = BatchHeader.newBuilder()
                .setSignerPublicKey(signer.getPublicKey().hex())
                .addAllTransactionIds(transactions.stream().map(sawtooth.sdk.protobuf.Transaction::getHeaderSignature).collect(Collectors.toList()))
                .build();
        String batchSignature = signer.sign(batchHeader.toByteArray());
        return Batch.newBuilder()
                .setHeader(batchHeader.toByteString())
                .setHeaderSignature(batchSignature)
                .addAllTransactions(transactions)
                .build();
    }

    private <T> T getRequest(String path, Class<T> clazz) throws IOException {
        String uri = validatorURI + path;
        return requestFactory.buildGetRequest(new GenericUrl(uri))
                .execute()
                .parseAs(clazz);
    }
}
