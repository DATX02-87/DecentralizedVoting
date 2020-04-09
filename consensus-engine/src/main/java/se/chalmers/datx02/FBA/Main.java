package se.chalmers.datx02.FBA;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.lib.impl.ZmqDriver;

import java.time.Duration;

public class Main {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

    private static Level log_level = Level.WARN; // DEFAULT: WARN
    private static String endpoint = "tcp://localhost:5050"; // DEFAULT: tcp://localhost:5050
    private static long exponential_retry_base = -1, // DEFAULT: -1 => Unset
                        exponential_retry_max = -1,
                        update_recv_timeout = -1,
                        max_log_size = -1;
    private static String storage_location = "";

    /**
     * Main method that consensus engine uses to start
     * @param args specifies the arguments to be used in the engine start up
     */
    public static void main(String[] args) {
        logger.info("Sawtooth PBFT Engine");

        // Try to parse args
        if(!parseArgs(args)) {
            logger.error("Failed to parse args, exiting");
            return;
        }

        // Set log level
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(log_level);

        // Set config
        Config pbft_config = new Config();
        if(exponential_retry_base > 0)
            pbft_config.exponentialRetryBase = Duration.ofMillis(exponential_retry_base);

        if(exponential_retry_max > 0)
            pbft_config.exponentialRetryMax = Duration.ofMillis(exponential_retry_max);

        if(update_recv_timeout > 0)
            pbft_config.updateRecvTimeout = Duration.ofMillis(update_recv_timeout);

        if(storage_location != "")
            pbft_config.setStorageLocation(storage_location);

        if(max_log_size > 0)
            pbft_config.setMaxLogSize(max_log_size);

        // Start up engine
        Engine pbft_engine = new Engine(pbft_config);

        ZmqDriver driver = new ZmqDriver(pbft_engine);

        driver.start(endpoint);


    }

    /**
     * Try to parse main args
     * @return Returns true if successfully parsed
     */
    private static boolean parseArgs(String[] args){
        try {
            if(args.length >= 1)
                switch(args[0]){
                    case "0":
                        log_level = Level.WARN;
                        break;
                    case "1":
                        log_level = Level.INFO;
                        break;
                    case "2":
                        log_level = Level.DEBUG;
                        break;
                    default:
                        log_level = Level.TRACE;
                        break;
                }
            if (args.length >= 2)
                endpoint = args[1];
            if (args.length >= 3)
                exponential_retry_base = Long.parseLong(args[2]);
            if (args.length >= 4)
                exponential_retry_max = Long.parseLong(args[3]);
            if (args.length >= 5)
                update_recv_timeout = Long.parseLong(args[4]);
            if (args.length >= 6)
                max_log_size = Long.parseLong(args[5]);
            if (args.length >= 7)
                storage_location = args[6];
        }
        catch(Exception e){
            return false;
        }

        return true;
    }
}
