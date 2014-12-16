package org.reactor.sonar.request;

import org.reactor.annotation.ReactorRequestParameter;

public class GetSonarPredefinedMetricRequestData {

    @ReactorRequestParameter(shortName = "v", required = false, description = "If set, will print out only metric value")
    private boolean valueOnly = false;

    public boolean isValueOnly() {
        return valueOnly;
    }

    public void setValueOnly(boolean valueOnly) {
        this.valueOnly = valueOnly;
    }
}
