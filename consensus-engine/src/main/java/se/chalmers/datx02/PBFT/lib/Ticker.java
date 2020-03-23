package se.chalmers.datx02.PBFT.lib;

import java.time.Duration;
import java.time.Instant;

public class Ticker {
    private Instant last;
    private Duration timeout;

    public Ticker(Duration period){
        last = Instant.now();

        timeout = period;
    }

    // Return true if ticked
    public boolean Tick(){
        Duration elapsed = Duration.between(Instant.now(), last);

        if(elapsed.compareTo(timeout) >= 0){
            last = Instant.now();
            return true;
        }

        return false;
    }
}
