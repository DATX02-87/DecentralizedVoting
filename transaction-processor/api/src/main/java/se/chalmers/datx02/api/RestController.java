package se.chalmers.datx02.api;

import com.google.protobuf.ByteString;
import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import sawtooth.sdk.protobuf.Transaction;
import se.chalmers.datx02.api.model.api.PostTransactionRequest;
import se.chalmers.datx02.api.model.api.PostTransactionResponse;
import se.chalmers.datx02.model.DataUtil;
import se.chalmers.datx02.model.TransactionPayload;

import java.io.IOException;

@Controller("/rest")
public class RestController {
    ValidatorService validatorService = ValidatorService.getInstance();

    @POST("(/transaction")
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
}
