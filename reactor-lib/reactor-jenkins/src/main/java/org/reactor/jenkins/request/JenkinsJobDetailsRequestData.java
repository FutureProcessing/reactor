package org.reactor.jenkins.request;

import org.reactor.annotation.ReactorRequestParameter;

public class JenkinsJobDetailsRequestData {

    @ReactorRequestParameter(required = true, shortName = "j", description = "Full name of jenkins job")
    private String jobName;

    @ReactorRequestParameter(shortName = "js", description = "Prints out only job status")
    private boolean jobStatusOnly;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public boolean isJobStatusOnly() {
        return jobStatusOnly;
    }

    public void setJobStatusOnly(boolean jobStatusOnly) {
        this.jobStatusOnly = jobStatusOnly;
    }
}
