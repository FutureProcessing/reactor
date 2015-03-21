package org.reactor.jenkins.response;

import org.reactor.jenkins.event.JobActivityEvent;
import org.reactor.response.ReactorResponse;

public class JobActivityEventResponse implements ReactorResponse {

    private final String jobName;
    private final String buildUrl;
    private final String phase;
    private final String status;

    public JobActivityEventResponse(JobActivityEvent jobActivityEvent) {
        this.jobName = jobActivityEvent.getJobName();
        this.buildUrl = jobActivityEvent.getBuildUrl();
        this.phase = jobActivityEvent.getPhase();
        this.status = jobActivityEvent.getStatus();
    }

    public String getJobName() {
        return jobName;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public String getPhase() {
        return phase;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toConsoleOutput() {
        return String.format("Jenkins Job status changed: %s\nBuild URL: %s\nBuild Phase: %s\nStatus: %s", jobName, buildUrl, phase, status);
    }
}
