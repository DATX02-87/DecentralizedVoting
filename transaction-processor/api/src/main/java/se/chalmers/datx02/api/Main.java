package se.chalmers.datx02.api;

import org.rapidoid.setup.App;

public class Main {



    public static void main(String[] args) {

        App.bootstrap(args);
        GetHandler getHandler = new GetHandler();
        TransactionBuilder transactionBuilder = new TransactionBuilder();

        //ValidatorCommunicator.init("http://127.0.0.1:8080", "0x011312f1f2356bv6b2456456b2456bv5");
        //ValidatorCommunicator v = ValidatorCommunicator.getInstance();

        //System.out.println(v.toString());

        //On.post("/candidate/vote").json((Transaction t) -> transactionBuilder.buildTransaction(t));
        //On.post("/candidate").json((Transaction t) -> transactionBuilder.buildTransaction(t));

        //On.get("/candidates").json((@Valid Transaction t) -> transactionBuilder.buildTransaction(t));
        //On.get("/candidates/candidate").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));
        //On.get("/candidates/candidate/votecount").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));
        //On.get("/totalvotes").json((Transaction t) -> (transactionBuilder.buildTransaction(t)));


    }

}