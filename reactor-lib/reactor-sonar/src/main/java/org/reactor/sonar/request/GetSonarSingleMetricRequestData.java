package org.reactor.sonar.request;

import org.reactor.annotation.ReactorRequestParameter;

public class GetSonarSingleMetricRequestData {

    @ReactorRequestParameter(shortName = "m", required = true)
    private String metricKey;

    @ReactorRequestParameter(shortName = "v", required = false)
    private boolean valueOnly = false;

    public String getMetricKey() {
        return metricKey;
    }

    public void setMetricKey(String metricKey) {
        this.metricKey = metricKey;
    }

    public boolean isValueOnly() {
        return valueOnly;
    }

    public void setValueOnly(boolean valueOnly) {
        this.valueOnly = valueOnly;
    }
}
