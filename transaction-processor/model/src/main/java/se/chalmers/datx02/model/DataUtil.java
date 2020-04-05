package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.state.GlobalState;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DataUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String GlobalStateToString(GlobalState state) {
        try {
            String json = objectMapper.writeValueAsString(state);
            return Base64.getEncoder().encodeToString(json.getBytes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static GlobalState StringToGlobalState(String s) {
        try {
            String json = new String(Base64.getDecoder().decode(s));
            return objectMapper.readValue(json, GlobalState.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static TransactionPayload StringToTransactionPayload(String s) {
        try {
            String json = new String(Base64.getDecoder().decode(s));
            return objectMapper.readValue(json, TransactionPayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String TransactionPayloadToString(TransactionPayload transactionPayload) {
        try {
            String json = objectMapper.writeValueAsString(transactionPayload);
            return Base64.getEncoder().encodeToString(json.getBytes());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hash(String value) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-512");
            instance.reset();
            instance.digest(value.getBytes());
            return Base64.getEncoder().encodeToString(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
