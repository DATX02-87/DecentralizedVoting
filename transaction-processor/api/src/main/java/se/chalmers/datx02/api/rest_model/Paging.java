package se.chalmers.datx02.api.rest_model;

import com.google.api.client.util.Key;

public class Paging {
    @Key
    private String start;
    @Key
    private String limit;
    @Key
    private String nextPosition;
    @Key
    private String next;

    public String getStart() {
        return start;
    }

    public String getLimit() {
        return limit;
    }

    public String getNextPosition() {
        return nextPosition;
    }

    public String getNext() {
        return next;
    }
}
