package org.reactor.jenkins.response;

import org.reactor.jenkins.event.JobActivityEvent;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public class JobActivityEventResponse implements ReactorResponse {

    private final JobActivityEvent jobActivityEvent;

    public JobActivityEventResponse(JobActivityEvent jobActivityEvent) {
        this.jobActivityEvent = jobActivityEvent;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderHeadLine("Jenkins Job status changed: %s", jobActivityEvent.getJobName());
        responseRenderer.renderTextLine("Build URL: %s", jobActivityEvent.getBuildUrl());
        responseRenderer.renderTextLine("Build Phase: %s", jobActivityEvent.getPhase());
        responseRenderer.renderTextLine("Status: %s", jobActivityEvent.getStatus());
    }
}
