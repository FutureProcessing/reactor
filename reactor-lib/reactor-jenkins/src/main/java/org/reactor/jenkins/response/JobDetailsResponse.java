package org.reactor.jenkins.response;

import static com.google.common.base.Optional.fromNullable;

import java.io.IOException;
import java.util.Date;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.NullableJobWithDetails;

public class JobDetailsResponse implements ReactorResponse {

    public static final String JOB_DETAILS_MESSAGE = "Job Details: %s";
    private final static Logger LOG = LoggerFactory.getLogger(JobDetailsResponse.class);
    private static final String LATEST_FAILED_BUILD_MESSAGE = "Latest failed build on: %tc";
    private static final String LATEST_SUCCESSFUL_BUILD_MESSAGE = "Latest successful build on: %tc";
    private static final String LATEST_COMPLETED_BUILD_MESSAGE = "Latest completed build on: %tc";
    private final JobWithDetails jenkinsJob;

    public JobDetailsResponse(JobWithDetails jenkinsJob) {
        this.jenkinsJob = new NullableJobWithDetails(jenkinsJob);
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderHeadLine(JOB_DETAILS_MESSAGE, jenkinsJob.getName());
        try {
            printIfPresent(responseRenderer, getLastCompletedBuild(), LATEST_COMPLETED_BUILD_MESSAGE);
            printIfPresent(responseRenderer, getLastFailedBuild(), LATEST_FAILED_BUILD_MESSAGE);
            printIfPresent(responseRenderer, getLastSuccessfulBuild(), LATEST_SUCCESSFUL_BUILD_MESSAGE);
        } catch (IOException e) {
            LOG.error("Something went wrong during job details retrieval.", e);
        }
    }

    private void printIfPresent(ReactorResponseRenderer responseRenderer, Optional<Build> build, String message)
            throws IOException {
        if (build.isPresent()) {
            BuildWithDetails buildDetails = build.get().details();
            responseRenderer.renderTextLine(message, new Date(buildDetails.getTimestamp()));
        }
    }

    private Optional<Build> getLastCompletedBuild() {
        return fromNullable(jenkinsJob.getLastCompletedBuild());
    }

    private Optional<Build> getLastFailedBuild() {
        return fromNullable(jenkinsJob.getLastFailedBuild());
    }

    private Optional<Build> getLastSuccessfulBuild() {
        return fromNullable(jenkinsJob.getLastSuccessfulBuild());
    }

}
