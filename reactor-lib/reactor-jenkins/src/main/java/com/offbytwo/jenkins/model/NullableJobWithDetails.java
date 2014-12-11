package com.offbytwo.jenkins.model;

import static com.google.common.base.Optional.fromNullable;

public class NullableJobWithDetails extends JobWithDetails {

    public NullableJobWithDetails(JobWithDetails jobWithDetails) {
        client = jobWithDetails.client;
        displayName = jobWithDetails.displayName;
        buildable = jobWithDetails.buildable;
        builds = jobWithDetails.builds;
        lastBuild = jobWithDetails.lastBuild;
        lastCompletedBuild = jobWithDetails.lastCompletedBuild;
        lastFailedBuild = jobWithDetails.lastFailedBuild;
        lastStableBuild = jobWithDetails.lastStableBuild;
        lastSuccessfulBuild = jobWithDetails.lastSuccessfulBuild;
        lastUnstableBuild = jobWithDetails.lastUnstableBuild;
        lastUnsuccessfulBuild = jobWithDetails.lastUnsuccessfulBuild;
        nextBuildNumber = jobWithDetails.nextBuildNumber;
        downstreamProjects = jobWithDetails.downstreamProjects;
        upstreamProjects = jobWithDetails.upstreamProjects;
    }

    @Override
    public Build getLastFailedBuild() {
        if (!fromNullable(lastFailedBuild).isPresent()) {
            return null;
        }
        return super.getLastFailedBuild();
    }

    @Override
    public Build getLastCompletedBuild() {
        if (!fromNullable(lastCompletedBuild).isPresent()) {
            return null;
        }
        return super.getLastCompletedBuild();
    }

    @Override
    public Build getLastSuccessfulBuild() {
        if (!fromNullable(lastSuccessfulBuild).isPresent()) {
            return null;
        }
        return super.getLastCompletedBuild();
    }
}
