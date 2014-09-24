package org.reactor.sonar.request;

import org.reactor.annotation.ReactorRequestParameter;

public class ListSonarMetricsRequestData {

    @ReactorRequestParameter(shortName = "m", required = false)
    private String metricNameFilter;

    public String getMetricNameFilter() {
        return metricNameFilter;
    }

    public void setMetricNameFilter(String metricNameFilter) {
        this.metricNameFilter = metricNameFilter;
    }
}
