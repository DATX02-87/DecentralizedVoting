package se.chalmers.datx02.PBFT.lib;

import java.time.Duration;
import java.time.Instant;

public class Timeout {

    public Timeout(Duration duration) {
        this.duration = duration;
        this.state = TimeoutState.Inactive;
        this.start = Instant.now();
    }

    public enum TimeoutState{
        Active,
        Inactive,
        Expired
    }

    private TimeoutState state;
    private Duration duration;
    private Instant start;

    public boolean checkExpired(){
        if(this.state == TimeoutState.Active && Duration.between(Instant.now(), this.start).compareTo(this.duration) == 1)
            this.state = TimeoutState.Expired;

        return (this.state == TimeoutState.Expired);
    }

    public void start(){
        this.state = TimeoutState.Active;
        this.start = Instant.now();
    }

    public void stop(){
        this.state = TimeoutState.Inactive;
        this.start = Instant.now();
    }

    public Duration duration(){
        return this.duration;
    }

    public boolean isActive(){
        return (this.state == TimeoutState.Active);
    }
}
