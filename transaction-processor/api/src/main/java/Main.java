import org.rapidoid.annotation.Valid;
import org.rapidoid.setup.App;
import org.rapidoid.setup.On;
import sawtooth.sdk.protobuf.Transaction;

public class Main {



    public static void main(String[] args) {

        App.bootstrap(args);
        GetHandler getHandler = new GetHandler();
        TransactionBuilder transactionBuilder = new TransactionBuilder();

        //On.post("/candidate/vote").json((Transaction t) -> transactionBuilder.buildTransaction(t));
        //On.post("/candidate").json((Transaction t) -> transactionBuilder.buildTransaction(t));

        //On.get("/candidates").json((@Valid Transaction t) -> transactionBuilder.buildTransaction(t));
        //On.get("/candidates/candidate").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));
        //On.get("/candidates/candidate/votecount").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));
        //On.get("/totalvotes").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));


    }

}