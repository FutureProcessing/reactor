package org.reactor.sonar.request;

import org.reactor.annotation.ReactorRequestParameter;

public class ListSonarMetricsRequestData {

    @ReactorRequestParameter(shortName = "m", description = "Metric name filter based on wildcard matching. If given, will print out all metrics that matches given filter.")
    private String metricNameFilter;

    public String getMetricNameFilter() {
        return metricNameFilter;
    }

    public void setMetricNameFilter(String metricNameFilter) {
        this.metricNameFilter = metricNameFilter;
    }
}
