package org.reactor.jenkins.response;

import static com.google.common.base.Optional.fromNullable;
import static java.lang.String.format;

import com.google.common.base.Optional;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.NullableJobWithDetails;

import org.reactor.response.StringReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JobDetailsResponse extends StringReactorResponse {

    private final static Logger LOG = LoggerFactory.getLogger(JobDetailsResponse.class);

    private static final String LATEST_FAILED_BUILD_MESSAGE = "Latest failed build on: %tc";
    private static final String LATEST_SUCCESSFUL_BUILD_MESSAGE = "Latest successful build on: %tc";
    private static final String LATEST_COMPLETED_BUILD_MESSAGE = "Latest completed build on: %tc";

    public static final String JOB_DETAILS_MESSAGE = "Job Details: %s";

    private final JobWithDetails jenkinsJob;

    public JobDetailsResponse(JobWithDetails jenkinsJob) {
        super(JOB_DETAILS_MESSAGE, jenkinsJob.getName());
        this.jenkinsJob = new NullableJobWithDetails(jenkinsJob);
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        try {
            printIfPresent(printWriter, getLastCompletedBuild(), LATEST_COMPLETED_BUILD_MESSAGE);
            printIfPresent(printWriter, getLastFailedBuild(), LATEST_FAILED_BUILD_MESSAGE);
            printIfPresent(printWriter, getLastSuccessfulBuild(), LATEST_SUCCESSFUL_BUILD_MESSAGE);
        } catch (IOException e) {
            LOG.error("Something went wrong during job details retrieval.", e);
        }
    }

    private void printIfPresent(PrintWriter printWriter, Optional<Build> build, String message)
            throws IOException {
        if (build.isPresent()) {
            BuildWithDetails buildDetails = build.get().details();
            printWriter.println(format(message, new Date(buildDetails.getTimestamp())));
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
