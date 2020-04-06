package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.state.GlobalState;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DataUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String GlobalStateToString(GlobalState state) {
        try {
            String json = objectMapper.writeValueAsString(state);
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static GlobalState StringToGlobalState(String s) {
        try {
            String json = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
            return objectMapper.readValue(json, GlobalState.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static TransactionPayload ByteArrToTransactionPayload(byte[] data) {
        try {
            return objectMapper.readValue(data, TransactionPayload.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] TransactionPayloadToByteArr(TransactionPayload transactionPayload) {
        try {
            return objectMapper.writeValueAsBytes(transactionPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(byte[] data) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-512");
            instance.reset();
            return bytesToHex(instance.digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toLowerCase().toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
