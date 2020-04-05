package se.chalmers.datx02.api;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import se.chalmers.datx02.api.model.TransactionDataToSignRequest;
import se.chalmers.datx02.api.model.TransactionDataToSignResponse;

@Controller("/rpc")
public class RpcController {
    ValidatorService validatorService = ValidatorService.getInstance();

    @POST(value = "get-transaction-data-to-sign")
    public TransactionDataToSignResponse getTransactionDataTo(TransactionDataToSignRequest request) {
        byte[] bytes = validatorService.buildTransactionHeader(request.getPublicKey(), request.getTransactionPayload());
        TransactionDataToSignResponse response = new TransactionDataToSignResponse();
        response.setDataToSign(bytes);
        return response;
    }
}
