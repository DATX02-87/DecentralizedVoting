package se.chalmers.datx02.api.model.validator;

import com.google.api.client.util.Key;

import java.util.List;

public class StateReponse {
    @Key
    private List<StateEntry> data;
    @Key
    private String link;
    @Key
    private String head;
    @Key
    private Paging paging;

    public String getLink() {
        return link;
    }

    public String getHead() {
        return head;
    }

    public Paging getPaging() {
        return paging;
    }

    public List<StateEntry> getData() {
        return data;
    }
}
