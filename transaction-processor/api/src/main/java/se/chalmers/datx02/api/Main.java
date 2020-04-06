package se.chalmers.datx02.api;

import org.rapidoid.setup.App;
import sawtooth.sdk.signing.Secp256k1Context;
import sawtooth.sdk.signing.Signer;

public class Main {
    public static void main(String[] args) {
        Secp256k1Context context = new Secp256k1Context();
        Signer s = new Signer(context, context.newRandomPrivateKey());
        ValidatorService.init("", "", s);
        App.bootstrap(args);
    }
}