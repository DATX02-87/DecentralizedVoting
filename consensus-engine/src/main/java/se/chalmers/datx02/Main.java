package se.chalmers.datx02;

import se.chalmers.datx02.lib.ZmqDriver;

public class Main {
    public static void main(String[] args) {
        // Get Endpoint
        String endpoint = "tcp://localhost:5050";

        // Start Driver
        ZmqDriver driver = new ZmqDriver(new DevmodeEngine());

        driver.start(endpoint);
    }
}
