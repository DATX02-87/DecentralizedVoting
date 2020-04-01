package se.chalmers.datx02.PBFT.lib.timing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class RetryUntilOk {
    private Duration base, max, delay;

    // -- EXAMPLE USAGE:
    /*
    RetryUntilOk example == new RetryUntilOk(base, max);

    while(true){
        try{
            FUNC HERE

            break;
        }
        catch(Exception e){
            Thread.sleep(example.getDelay());
            example.check();
        }
    }
    */
    //

    public RetryUntilOk(Duration base, Duration max){
        this.base = base;
        this.max = max;
        this.delay = base;
    }

    public long getDelay(){
        return delay.toMillis();
    }

    public void check(){
        if(delay.compareTo(max) == -1){
            try {
                delay = delay.multipliedBy(2);
            }
            // Overflow
            catch(Exception e){
                delay = ChronoUnit.FOREVER.getDuration();
            }

            if(delay.compareTo(max) == 1){
                delay = max;
            }
        }
    }
}
