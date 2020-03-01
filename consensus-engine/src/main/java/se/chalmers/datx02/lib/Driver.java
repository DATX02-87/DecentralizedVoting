package se.chalmers.datx02.lib;

/**
 * Describes a driver for the consensus engine, handles communication from the validator to the engine
 */
public interface Driver {
    /**
     * Start the driver with the server running on the specified endpoint
     * @param endpoint the endpoint to run the server on
     */
    void start(String endpoint);

    /**
     * Stop the driver, stopping the consensus engine
     */
    void stop();
}
