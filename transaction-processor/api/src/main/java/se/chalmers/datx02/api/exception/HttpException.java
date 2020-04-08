package se.chalmers.datx02.api.exception;

public class HttpException extends RuntimeException {
    private final int statusCode;
    public HttpException(int statusCode, String message) {
        super("HTTP error " + statusCode + " \t" + message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
