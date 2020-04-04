package se.chalmers.datx02.api;

import org.rapidoid.setup.App;
import org.rapidoid.setup.On;
import sawtooth.sdk.signing.Secp256k1Context;
import sawtooth.sdk.signing.Secp256k1PrivateKey;
import sawtooth.sdk.signing.Signer;
import se.chalmers.datx02.model.state.Election;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        String validatorAddress = "";
        String stateAddress = "";
        Signer signer = new Signer(new Secp256k1Context(), new Secp256k1PrivateKey(new byte[]{}));
        ValidatorService.init(validatorAddress, stateAddress, signer);

        On.get("/candidates").json((String election) -> {
            ValidatorService validatorService = ValidatorService.getInstance();
            Election el = validatorService.getState().getElections().get(election);
            if (el == null) {
                return Collections.emptySet();
            }
            return el.getCandidates().keySet();
        });
        // TODO
    }

}