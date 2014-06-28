package org.reactor.jenkins.request;

import org.reactor.annotation.ReactorRequestParameter;

public class JenkinsJobDetailsRequestData {

    @ReactorRequestParameter(required = true, name = "jobName", shortName = "j")
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
