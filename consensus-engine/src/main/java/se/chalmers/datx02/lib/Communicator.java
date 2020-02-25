package se.chalmers.datx02.lib;

import sawtooth.sdk.protobuf.Message;
import se.chalmers.datx02.lib.models.ConsensusFuture;

import java.util.concurrent.Future;

/**
 * Communicator class representing the python stream class from the sawtooth sdk
 * This class represents an abstraction of communicating from the consensus engine with a validator process
 *
 */
public interface Communicator extends AutoCloseable {
    /**
     * Get the url that the communicator is listening on
     * @return the url
     */
    String getUrl();

    /**
     * Get the id of the socket ( the id that the validator is supposed to be communicating with)
     * @return the id, as a byte array
     */
    byte[] getZmqId();

    /**
     * Send a message to the validator, expecting a response
     * @param content the data to send
     * @param messageType the type of the data that is to be sent
     * @return a future, resolving to the answer from the validator
     */
    ConsensusFuture send(byte[] content, Message.MessageType messageType);

    /**
     * Continue communicating with the same correlation id.
     * For example when wanting to keep the context of a request response communications channel
     * @param content the content to send
     * @param correlationId the correlation id to use with the
     * @param messageType the message type of the request
     */
    void sendBack(byte[] content, String correlationId, Message.MessageType messageType);

    /**
     * Receive data from the validator as a future
     * @return the message to receive wrapped in a future
     */
    Future<Message> receive();

    /**
     * Blocking call to wait for the communicator to become ready
     */
    void waitForReady();

    /**
     * Get if the communicator is ready or not
     * @return true if ready, false otherwise
     */
    boolean isReady();


}
