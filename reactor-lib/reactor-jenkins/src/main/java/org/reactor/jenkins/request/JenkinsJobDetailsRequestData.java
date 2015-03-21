package org.reactor.jenkins.request;

import org.reactor.annotation.ReactorRequestParameter;

public class JenkinsJobDetailsRequestData {

    @ReactorRequestParameter(required = true, shortName = "j", description = "Full name of jenkins job")
    private String jobName;

    public JenkinsJobDetailsRequestData() {
    }

    public JenkinsJobDetailsRequestData(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
