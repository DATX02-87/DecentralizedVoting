package se.chalmers.datx02.testutils;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class Block extends GenericJson {
    @Key(value = "header_signature")
    private String headerSignature;

    @Key
    private Header header;

    public String getHeaderSignature() {
        return headerSignature;
    }

    public Header getHeader() {
        return header;
    }
}
