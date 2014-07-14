package org.reactor.sonar.request;

import org.reactor.annotation.ReactorRequestParameter;

public class GetSonarSingleMetricRequestData {

    @ReactorRequestParameter(shortName = "m", required = true)
    private String metricKey;

    public String getMetricKey() {
        return metricKey;
    }

    public void setMetricKey(String metricKey) {
        this.metricKey = metricKey;
    }
}
