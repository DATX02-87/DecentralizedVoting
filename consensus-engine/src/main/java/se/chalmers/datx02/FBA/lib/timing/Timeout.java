package se.chalmers.datx02.FBA.lib.timing;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;

public class Timeout implements Serializable  {
    private static final long serialVersionUID = 1L;

    /**
     * Different states for the timeout
     */
    public enum TimeoutState{
        Active,
        Inactive,
        Expired
    }

    private TimeoutState state;
    private Duration duration;
    private Instant start;

    /**
     * Initializes a new timeout
     * @param duration specifies the duration of the timeout
     */
    public Timeout(Duration duration) {
        this.duration = duration;
        this.state = TimeoutState.Inactive;
        this.start = Instant.now();
    }

    /**
     * Checks if the timeout has expired
     * @return returns true if expired
     */
    public boolean checkExpired(){
        if(this.state == TimeoutState.Active && Duration.between(Instant.now(), this.start).compareTo(this.duration) == 1)
            this.state = TimeoutState.Expired;

        return (this.state == TimeoutState.Expired);
    }

    /**
     * Starts the timeout
     */
    public void start(){
        this.state = TimeoutState.Active;
        this.start = Instant.now();
    }

    /**
     * Stops the timeout
     */
    public void stop(){
        this.state = TimeoutState.Inactive;
        this.start = Instant.now();
    }

    /**
     * The duration of the timeout
     * @return returns the duration
     */
    public Duration duration(){
        return this.duration;
    }

    /**
     * Checks if the timeout is active
     * @return returns true if active
     */
    public boolean isActive(){
        return (this.state == TimeoutState.Active);
    }
}
