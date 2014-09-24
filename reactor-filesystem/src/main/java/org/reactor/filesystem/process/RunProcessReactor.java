package org.reactor.filesystem.process;

import static org.reactor.request.ArgumentsParser.parseArguments;
import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ExceptionReactorResponse;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ReactOn(value = "run", description = "Runs operating system process and prints out it's result")
public class RunProcessReactor extends AbstractAnnotatedReactor<String> {

    private final static Logger LOG = LoggerFactory.getLogger(RunProcessReactor.class);

    public RunProcessReactor() {
        super(String.class);
    }

    @Override
    protected ReactorResponse doReact(ReactorRequest<String> reactorRequest) {
        try {
            String processResult = runProcess(parseArguments(reactorRequest.getRequestData()));
            return new StringReactorResponse(processResult);
        } catch (IOException | InterruptedException e) {
            LOG.error("An error occurred while running process", e);
            return new ExceptionReactorResponse(e);
        }
    }

    @VisibleForTesting
    String runProcess(String... processArguments) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(processArguments);
        Process process = processBuilder.start();
        process.waitFor();
        return IOUtils.toString(process.getInputStream());
    }
}
