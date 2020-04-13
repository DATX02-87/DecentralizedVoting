package se.chalmers.datx02.testutils;

import com.palantir.docker.compose.connection.DockerMachine;
import com.palantir.docker.compose.execution.DockerConfiguration;
import com.palantir.docker.compose.execution.DockerExecutable;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DockerExecutor {
    private final DockerExecutable dockerExecutable;

    private final PrintStream output;

    private final DockerConfiguration configuration;

    public DockerExecutor(String... env) {
        if (env.length % 2 != 0) {
            throw new RuntimeException("You have to supply even env args to be able to parse them to a map");
        }
        Map<String, String> environment = new HashMap<>();


        for (int i = 0; i < env.length; i = i + 2) {
            environment.put(env[i], env[i + 1]);
        }
        this.configuration = DockerMachine.localMachine()
                .withEnvironment(environment)
                .build();
        this.dockerExecutable = DockerExecutable.builder()
                .dockerConfiguration(configuration)
                .build();
        this.output = System.out;
    }


    public void performCommandWithException(String... cmd) throws Exception {
        int retVal = performCommandExitCode(cmd);
        if (retVal != 0) {
            throw new Exception("Return value was " + retVal);
        }
    }

    public int performCommandExitCode(String... cmd) {
        try {
            Process execute = dockerExecutable.execute(cmd);
            StreamGobbler err = new StreamGobbler(execute.getErrorStream());
            StreamGobbler out = new StreamGobbler(execute.getInputStream());
            err.start();
            out.start();
            return execute.waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DockerExecutable getDockerExecutable() {
        return dockerExecutable;
    }

    public DockerConfiguration getConfiguration() {
        return configuration;
    }

    private static class StreamGobbler extends Thread {
        InputStream is;

        // reads everything from is until empty.
        public StreamGobbler(InputStream is) {
            this.is = is;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }


}
