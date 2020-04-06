package se.chalmers.datx02.api;

import com.google.protobuf.ByteString;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.GET;
import org.rapidoid.annotation.POST;
import org.rapidoid.annotation.Param;
import sawtooth.sdk.protobuf.Transaction;
import se.chalmers.datx02.api.model.api.PostTransactionRequest;
import se.chalmers.datx02.api.model.api.PostTransactionResponse;
import se.chalmers.datx02.model.DataUtil;
import se.chalmers.datx02.model.TransactionPayload;
import se.chalmers.datx02.model.state.Election;
import se.chalmers.datx02.model.state.GlobalState;

import java.io.IOException;
import java.util.Map;

@Controller("/rest")
public class RestController {
    ValidatorService validatorService = ValidatorService.getInstance();

    @POST("/transaction")
    public PostTransactionResponse postTransaction(PostTransactionRequest request) throws IOException {
        String payload = DataUtil.TransactionPayloadToString(request.getPayload());
        Transaction transaction = Transaction.newBuilder()
                .setHeaderSignature(request.getHeader())
                .setPayload(ByteString.copyFromUtf8(payload))
                .setHeaderSignature(request.getSignature())
                .build();
        String batchId = validatorService.postTransaction(transaction);
        return new PostTransactionResponse(request.getSignature(), batchId);
    }

    @GET("/state")
    public GlobalState getGlobalState() throws IOException {
        return validatorService.getState();
    }

    @GET("/elections")
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
