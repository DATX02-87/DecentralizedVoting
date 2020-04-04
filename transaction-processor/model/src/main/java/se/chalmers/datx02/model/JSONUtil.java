package se.chalmers.datx02.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.chalmers.datx02.model.state.GlobalState;

public class JSONUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public static String GlobalStateToString(GlobalState state) {
        try {
            return objectMapper.writeValueAsString(state);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static GlobalState StringToGlobalState(String s) {
        try {
            return objectMapper.readValue(s, GlobalState.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
