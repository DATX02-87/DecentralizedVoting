package se.chalmers.datx02.api;

import org.rapidoid.annotation.Controller;
import org.rapidoid.annotation.POST;
import org.rapidoid.http.Req;
import org.rapidoid.http.Resp;
import se.chalmers.datx02.api.model.api.TransactionDataToSignRequest;
import se.chalmers.datx02.api.model.api.TransactionDataToSignResponse;

@Controller("/rpc")
public class RpcController {
    ValidatorService validatorService = ValidatorService.getInstance();

    @POST(value = "get-transaction-data-to-sign")
    public TransactionDataToSignResponse getTransactionDataTo(Req req, Resp resp, TransactionDataToSignRequest request) {
        resp.header("Access-Control-Allow-Origin", "*");
        byte[] bytes = validatorService.buildTransactionHeader(request.getPublicKey(), request.getTransactionPayload());
        TransactionDataToSignResponse response = new TransactionDataToSignResponse();
        response.setDataToSign(bytes);
        return response;
    }
}
