package se.chalmers.datx02;

import se.chalmers.datx02.lib.Engine;
import se.chalmers.datx02.lib.Service;
import se.chalmers.datx02.lib.StartupState;
import se.chalmers.datx02.lib.models.DriverUpdate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class DevmodeEngine implements Engine {

    @Override
    public void start(BlockingQueue<DriverUpdate> updates, Service service, StartupState startupState) {

    }

    @Override
    public void stop() {

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
