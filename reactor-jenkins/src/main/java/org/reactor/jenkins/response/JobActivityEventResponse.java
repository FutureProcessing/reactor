package org.reactor.jenkins.response;

import static java.lang.String.format;
import org.reactor.jenkins.event.JobActivityEvent;
import org.reactor.response.StringReactorResponse;
import java.io.PrintWriter;

public class JobActivityEventResponse extends StringReactorResponse {

    private final JobActivityEvent jobActivityEvent;

    public JobActivityEventResponse(JobActivityEvent jobActivityEvent) {
        super("Jenkins Job status changed: %s", jobActivityEvent.getJobName());
        this.jobActivityEvent = jobActivityEvent;
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        printWriter.println(format("Build URL: %s", jobActivityEvent.getBuildUrl()));
        printWriter.println(format("Build Phase: %s", jobActivityEvent.getPhase()));
        printWriter.println(format("Status: %s", jobActivityEvent.getStatus()));
    }
}
