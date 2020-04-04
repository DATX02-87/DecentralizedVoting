package se.chalmers.datx02.model.exception;

public class ReducerException extends Exception {
    public ReducerException(String message) {
        super(message);
    }

    public ReducerException(String message, Throwable cause) {
        super(message, cause);
    }
}
