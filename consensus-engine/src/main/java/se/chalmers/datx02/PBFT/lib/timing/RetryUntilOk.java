package se.chalmers.datx02.PBFT.lib.timing;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Callable;

public class RetryUntilOk {
    private Duration base, max, delay;

    public RetryUntilOk(Duration base, Duration max){
        this.base = base;
        this.max = max;
        this.delay = base;
    }

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

    private long getDelay(){
        return delay.toMillis();
    }

    private void check(){
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
