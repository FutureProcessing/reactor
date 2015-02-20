package org.reactor.jenkins.response;

import org.reactor.response.StringReactorResponse;

public class JobBuildQueuedResponse extends StringReactorResponse {

    private final String jobName;

    public JobBuildQueuedResponse(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    @Override
    public String toConsoleOutput() {
        return String.format("Job queued: %s", jobName);
    }
}
