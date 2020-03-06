package se.chalmers.datx02;

import se.chalmers.datx02.lib.Engine;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.StartupState;
import se.chalmers.datx02.lib.models.DriverUpdate;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import sawtooth.sdk.protobuf.*;

public class DevmodeEngine implements Engine {
    private boolean running = false;

    @Override
    public void start(BlockingQueue<DriverUpdate> updates, Service service, StartupState startupState) {
        DevmodeService serviceDev = new DevmodeService(service);

        ConsensusBlock chainHead = startupState.getChainHead();
        int wait_time = serviceDev.calculateWaitTime(chainHead.block_id.clone());

        boolean published_at_height = false;
        Instant start = Instant.now(); // Get time

        serviceDev.initializeBlock();

        running = true;

        /*
        * 1. Wait for an incoming message
        * 2. Check for exit
        * 3. Handle the message
        * 4. Check for publishing
         */
        DriverUpdate incoming_message = null;
        while(running){
            try {
                // TODO: This might be wrong, double check this
                incoming_message = updates.poll(Duration.ofMillis(10).getSeconds(), TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Disconnected from validator");
                break;
            }

            System.out.println("Received message: " + incoming_message.getMessageType());

            // TODO: Replace with correct Messagetypes
            // TODO: Finish the cases
            switch(incoming_message.getMessageType()){
                case CONSENSUS_SHUTDOWN:
                    this.stop();
                    break;
                case CONSENSUS_NEW_BLOCK:
                    System.out.println("Checking consensus data: ");

                    if( == serviceDev.NULL_BLOCK_IDENTIFIER){
                        System.out.println("WARNING: Received genesis block; ignoring");
                        continue;
                    }


                    break;
                case CONSENSUS_VALID_BLOCK:

                    break;
                case CONSENSUS_COMMIT_BLOCK:

                    break;
                case CONSENSUS_PEERMESSAGE:

                    break;
                default:
                    // Ignore all other message types
                    break;
            }

            // Publish block when timer expires
            if(!published_at_height && Duration.between(Instant.now(), start).getSeconds() > wait_time){
                System.out.println("Timer expired - publishing block");
                byte[] new_blockId = serviceDev.finalizeBlock();
                published_at_height = true;

                serviceDev.broadcastPublishedBlock(new_blockId);
            }
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String getName() {
        return "Devmode";
    }

    @Override
    public Map<String, String> additionalProtocol() {
        return Collections.<String, String>emptyMap();
    }
}
