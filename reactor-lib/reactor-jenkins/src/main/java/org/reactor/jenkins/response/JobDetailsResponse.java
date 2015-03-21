package org.reactor.jenkins.response;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.NullableJobWithDetails;
import org.reactor.response.ReactorResponse;

import java.io.IOException;
import java.util.Date;

public class JobDetailsResponse implements ReactorResponse {

    private final String displayName;
    private final Date lastCompletedBuild;
    private final Date lastFailedBuild;
    private final Date lastSuccessfulBuild;

    public JobDetailsResponse(JobWithDetails job) {
        job = new NullableJobWithDetails(job);
        this.lastSuccessfulBuild = buildDate(job.getLastSuccessfulBuild());
        this.lastFailedBuild = buildDate(job.getLastFailedBuild());
        this.lastCompletedBuild = buildDate(job.getLastCompletedBuild());
        this.displayName = job.getDisplayName();
    }

    private Date buildDate(Build build) {
        if (build == null) {
            return null;
        }
        try {
            return new Date(build.details().getTimestamp());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String toConsoleOutput() {
        return String.format("Job Details: %s\nLatest failed build on: %s\nLatest successful build on: %s\nLatest completed build on: %s", displayName, lastFailedBuild, lastSuccessfulBuild, lastCompletedBuild);
    }

    public Date getLastSuccessfulBuild() {
        return lastSuccessfulBuild;
    }

    public Date getLastFailedBuild() {
        return lastFailedBuild;
    }

    public Date getLastCompletedBuild() {
        return lastCompletedBuild;
    }

    public String getDisplayName() {
        return displayName;
    }
}
