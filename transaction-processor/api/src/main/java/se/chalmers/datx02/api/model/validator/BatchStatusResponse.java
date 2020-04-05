package se.chalmers.datx02.api.model.validator;

import com.google.api.client.util.Key;

import java.util.List;

public class BatchStatusResponse {
    @Key
    private String link;
    @Key
    private List<BatchStatus> data;

    public String getLink() {
        return link;
    }

    public List<BatchStatus> getData() {
        return data;
    }
}
