package se.chalmers.datx02.FBA.lib.timing;

import java.time.Duration;
import java.time.Instant;

public class Ticker {
    private Instant last;
    private Duration timeout;

    /**
     * Initializes a ticker
     * @param period specifies the period to tick
     */
    public Ticker(Duration period){
        last = Instant.now();

        timeout = period;
    }

    /**
     * Checks if the elapsed time has bypassed the timeout duration
     * @return returns true if ticked
     */
    public boolean Tick(){
        Duration elapsed = Duration.between(Instant.now(), last);

        if(elapsed.compareTo(timeout) >= 0){
            last = Instant.now();
            return true;
        }

        return false;
    }
}
