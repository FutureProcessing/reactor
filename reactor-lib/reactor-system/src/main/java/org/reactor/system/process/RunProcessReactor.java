package org.reactor.system.process;

import static org.reactor.request.ArgumentsParser.parseArguments;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.reactor.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ExceptionReactorResponse;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;

import com.google.common.annotations.VisibleForTesting;

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
        } catch (IOException | InterruptedException | TimeoutException e) {
            LOG.error("An error occurred while running process", e);
            return new ExceptionReactorResponse(e);
        }
    }

    @VisibleForTesting
    String runProcess(String... processArguments) throws IOException, InterruptedException, TimeoutException {
        return new ProcessExecutor().command(processArguments).readOutput(true).execute().outputUTF8();
    }
}
