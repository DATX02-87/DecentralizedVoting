package se.chalmers.datx02.api;

import sawtooth.sdk.protobuf.BatchList;
import sawtooth.sdk.protobuf.BatchHeader;
import sawtooth.sdk.protobuf.Transaction;
import sawtooth.sdk.signing.PrivateKey;
import sawtooth.sdk.signing.Secp256k1Context;
import sawtooth.sdk.signing.Signer;
import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;

public class TransactionBuilder {

    public enum Action{
        AddCandidate,
        getVoteCount,
        Vote
    }
    private Secp256k1Context context = new Secp256k1Context();
    private PrivateKey privateKey = context.newRandomPrivateKey();
    private Signer signer = new Signer(context, privateKey);

    public void buildTransaction(Transaction t) {
        
    }
}