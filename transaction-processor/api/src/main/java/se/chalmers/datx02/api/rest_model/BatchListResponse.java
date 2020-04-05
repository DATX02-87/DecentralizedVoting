package se.chalmers.datx02.api.rest_model;

import com.google.api.client.util.Key;

public class BatchListResponse {
    @Key
    private String link;

    public String getLink() {
        return link;
    }
}
