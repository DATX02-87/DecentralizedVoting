package se.chalmers.datx02;

import picocli.CommandLine;
import se.chalmers.datx02.devmode.DevmodeEngine;
import se.chalmers.datx02.lib.Driver;
import se.chalmers.datx02.lib.impl.ZmqDriver;

@CommandLine.Command(name = "Hyperledger Sawtooth Devmode engine Java", mixinStandardHelpOptions = true, version = "1.0")
public class Application implements Runnable {
    @CommandLine.Option(names = {"-e", "--endpoint"}, description = "Validator endpoint", required = true)
    private String endpoint;


    public static void main(String[] args) {
        System.exit(new CommandLine(new Application()).execute(args));
    }

    @Override
    public void run() {
        Driver driver = new ZmqDriver(new DevmodeEngine());
        driver.start(endpoint);
    }

}
