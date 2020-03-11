package se.chalmers.datx02.testutils;

import com.google.api.client.util.Key;

import java.util.List;

public class Response {
    @Key
    private String head;
    @Key
    private String link;
    @Key
    private List<Block> data;

    public String getHead() {
        return head;
    }

    public String getLink() {
        return link;
    }

    public List<Block> getData() {
        return data;
    }
}

