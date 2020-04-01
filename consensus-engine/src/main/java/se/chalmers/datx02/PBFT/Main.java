package se.chalmers.datx02.PBFT;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.chalmers.datx02.lib.impl.ZmqDriver;

import java.time.Duration;

public class Main {
    private enum LogLevel{
        Warn,
        Info,
        Debug,
        Trace
    }

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static String log_config; // UNUSED
    private static LogLevel log_level; // UNUSED
    private static String endpoint = "tcp://localhost:5050"; // DEFAULT: tcp://localhost:5050
    private static long exponential_retry_base = -1, // DEFAULT: -1 => Unset
                        exponential_retry_max = -1,
                        update_recv_timeout = -1,
                        max_log_size = -1;
    private static String storage_location = "";

    public static void main(String[] args) {
        logger.info("Sawtooth PBFT Engine");

        // Try to parse args
        if(!parseArgs(args)) {
            logger.error("Failed to parse args, exiting");
            return;
        }

        // TODO: Init log config (NOT REQUIRED)

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
        // TODO: Parse log level & config (NOT REQUIRED)

        try {
            if (args.length >= 1)
                endpoint = args[0];
            if (args.length >= 2)
                exponential_retry_base = Long.parseLong(args[1]);
            if (args.length >= 3)
                exponential_retry_max = Long.parseLong(args[2]);
            if (args.length >= 4)
                update_recv_timeout = Long.parseLong(args[3]);
            if (args.length >= 5)
                max_log_size = Long.parseLong(args[4]);
            if (args.length >= 6)
                storage_location = args[5];
        }
        catch(Exception e){
            // FAILED TO PARSE, EXIT
            return false;
        }

        return true;
    }
}
