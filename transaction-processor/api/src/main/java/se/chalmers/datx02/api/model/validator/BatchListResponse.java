package se.chalmers.datx02.api.model.validator;

import com.google.api.client.util.Key;

public class BatchListResponse {
    @Key
    private String link;

    public String getLink() {
        return link;
    }
}
