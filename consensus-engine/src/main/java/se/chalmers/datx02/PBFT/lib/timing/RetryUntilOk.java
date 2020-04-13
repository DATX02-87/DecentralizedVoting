package se.chalmers.datx02.PBFT.lib.timing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

public class RetryUntilOk {
    private Duration base, max, delay;

    /**
     * Initializes a new RetryUntilOk object
     * @param base the base duration
     * @param max the max duration
     */
    public RetryUntilOk(Duration base, Duration max){
        this.base = base;
        this.max = max;
        this.delay = base;
    }

    /**
     * Retries calling function until exception is now thrown
     * @param func to be called
     * @param <R> specifies the generic result to be returned
     * @return returns the generic result
     */
    public<R> R run(Callable<R> func){
        while(true) {
            try {
                return func.call();
            } catch (Exception e) {
                try {
                    Thread.sleep(this.getDelay());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.check();
            }
        }
    }

    /**
     * Returns the delay in milliseconds
     * @return the delay in milliseconds
     */
    private long getDelay(){
        return delay.toMillis();
    }

    /**
     * Compares the delay to the max value to see if the max value has been bypassed
     */
    private void check(){
        if(delay.compareTo(max) < 0){
            try {
                delay = delay.multipliedBy(2);
            }
            // Overflow
            catch(Exception e){
                delay = ChronoUnit.FOREVER.getDuration();
            }

            if(delay.compareTo(max) > 0){
                delay = max;
            }
        }
    }
}
