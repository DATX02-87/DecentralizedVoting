package se.chalmers.datx02.api.model.validator;

import com.google.api.client.util.Key;

public class InvalidTransaction {
    @Key
    private String id;
    @Key
    private String message;
    @Key
    private String extendedData;

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getExtendedData() {
        return extendedData;
    }
}
