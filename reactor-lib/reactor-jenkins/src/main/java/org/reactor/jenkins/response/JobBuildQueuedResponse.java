package org.reactor.jenkins.response;

import org.reactor.response.StringReactorResponse;

public class JobBuildQueuedResponse extends StringReactorResponse {

    public JobBuildQueuedResponse(String jobName) {
        super("Job queued: %s", jobName);
    }
}
