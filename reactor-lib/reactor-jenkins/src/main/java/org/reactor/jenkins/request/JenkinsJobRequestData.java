package org.reactor.jenkins.request;

import org.reactor.annotation.ReactorRequestParameter;

public class JenkinsJobRequestData {

    @ReactorRequestParameter(required = true, shortName = "j", description = "Full name of jenkins job")
    private String jobName;

    public JenkinsJobRequestData() {
    }

    public JenkinsJobRequestData(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
