package se.chalmers.datx02.api;

import com.google.protobuf.ByteString;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Param;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;

import sawtooth.sdk.protobuf.Transaction;
import sawtooth.sdk.protobuf.TransactionHeader;
import sawtooth.sdk.signing.*;
import se.chalmers.datx02.api.exception.HttpException;
import se.chalmers.datx02.api.model.api.PostTransactionRequest;
import se.chalmers.datx02.api.model.api.PostTransactionResponse;
import se.chalmers.datx02.model.DataUtil;
import se.chalmers.datx02.model.exception.ReducerException;
import se.chalmers.datx02.model.state.Election;
import se.chalmers.datx02.model.state.GlobalState;


import java.io.IOException;
import java.util.Map;

@Controller("/rest")
public class RestController {
    ValidatorService validatorService = ValidatorService.getInstance();

    @POST(value = "/transaction")
    public PostTransactionResponse postTransaction(Req req, Resp resp, PostTransactionRequest request) throws IOException {
        resp.header("Access-Control-Allow-Origin", "*");
        TransactionHeader header = TransactionHeader.parseFrom(request.getHeader());
        try {
            validatorService.testTransaction(new se.chalmers.datx02.model.Transaction(request.getPayload(), header.getSignerPublicKey()));
        } catch (ReducerException e) {
            throw new HttpException(400, "Transaction invalid: \n" + e.getMessage());
        }
        byte[] payload = DataUtil.TransactionPayloadToByteArr(request.getPayload());
        Transaction transaction = Transaction.newBuilder()
                .setHeader(ByteString.copyFrom(request.getHeader()))
                .setPayload(ByteString.copyFrom(payload))
                .setHeaderSignature(request.getSignature())
                .build();
        String batchId = validatorService.postTransaction(transaction);
        return new PostTransactionResponse(request.getSignature(), batchId);
    }

    @GET(value = "/state")
    public GlobalState getGlobalState(Req req, Resp resp) throws IOException {
        resp.header("Access-Control-Allow-Origin", "*");
        return validatorService.getState();
    }

    @GET(value = "/elections")
    public Map<String, Election> getEligibleElections(@Param("key") String key) throws IOException, NullPointerException{
        Map <String, Election> map = null;
        GlobalState state = validatorService.getState();

        for(Map.Entry<String, Election> e : state.getElections().entrySet()){
            if (e.getKey().equals(key))
                map.put(key, e.getValue());
        }
        return map;
    }



}
