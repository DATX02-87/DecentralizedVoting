package se.chalmers.datx02.model;

import sawtooth.sdk.processor.Utils;

import java.nio.charset.StandardCharsets;

public class Adressing {
    public static final String FAMILY_NAME = "dcvs02";
    public static final String FAMILY_VERSION = "1.0";

    public static String makeMasterAddress(String masterStateName) {
        String nameSpace = Utils.hash512(FAMILY_NAME.getBytes(StandardCharsets.UTF_8)).substring(0, 6);
        String hashedName = Utils.hash512(masterStateName.getBytes(StandardCharsets.UTF_8));
        return nameSpace + hashedName.substring(0, 64);
    }
}
