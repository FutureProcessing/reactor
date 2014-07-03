package org.reactor.sonar;

import org.reactor.annotation.ReactorRequestParameter;

public class SonarRequestData {

    @ReactorRequestParameter(required = true, shortName = "k")
    private String projectKey;

    @ReactorRequestParameter(required = true, shortName = "m")
    private String metrics;

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

}
