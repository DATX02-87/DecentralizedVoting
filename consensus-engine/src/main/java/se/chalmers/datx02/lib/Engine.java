package se.chalmers.datx02.lib;

import se.chalmers.datx02.lib.models.DriverUpdate;
import se.chalmers.datx02.lib.models.StartupState;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public interface Engine {

    /**
     * Called after the engine is initialized, when a connection to the
     *         validator has been established. Notifications from the
     *         validator are sent along UPDATES. SERVICE is used to send
     *         requests to the validator.
     */
    void start(BlockingQueue<DriverUpdate> updates, Service service, StartupState startupState);

    /**
     * Called before the engine is dropped
     * in order to give the engine a chance to notify peers and clean up.
     */
    void stop();

    /**
     * Get the version of this engine.
     */
    String getVersion();

    /**
     * Get the name of the engine, typically the algorithm being implemented.
     */
    String getName();

    /**
     *  Any additional protocol name/version pairs this engine supports
     */
    Map<String, String> additionalProtocol();

}
