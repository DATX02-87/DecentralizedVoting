package se.chalmers.datx02.api;

import org.rapidoid.setup.App;
import org.rapidoid.setup.On;
import sawtooth.sdk.signing.Secp256k1Context;
import sawtooth.sdk.signing.Signer;
import se.chalmers.datx02.api.exception.HttpException;
import se.chalmers.datx02.model.Adressing;

public class Application {
    public static void main(String[] args) {
        Secp256k1Context context = new Secp256k1Context();
        Signer s = new Signer(context, context.newRandomPrivateKey());
        String stateAddress = Adressing.makeMasterAddress(args[1]);
        ValidatorService.init(args[0], stateAddress, s);
        App.bootstrap(new String[]{});
        On.error(HttpException.class).handler((req, resp, error) -> {
            HttpException exc = (HttpException) error;
            resp.header("Access-Control-Allow-Origin", "*");
            return resp.code(exc.getStatusCode()).result(exc.getMessage()).done();
        });
        On.options("/*").plain((req, resp) -> {
            resp.header("Access-Control-Allow-Origin", "*");
            resp.header("Access-Control-Allow-Headers", "*");
            resp.body(new byte[]{});
            return resp;
        });
    }
}