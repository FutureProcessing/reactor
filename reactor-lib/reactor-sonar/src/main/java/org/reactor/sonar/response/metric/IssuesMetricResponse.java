package org.reactor.sonar.response.metric;

import org.reactor.sonar.data.MetricWithValueResource;
import org.reactor.sonar.response.AbstractMetricValueResponse;

public class IssuesMetricResponse extends AbstractMetricValueResponse {

    private final MetricWithValueResource allIssuesMetric;
    private final MetricWithValueResource infoIssuesMetric;

    public IssuesMetricResponse(MetricWithValueResource allIssuesMetric, MetricWithValueResource infoIssuesMetric,
                                boolean valueOnly) {
        super(valueOnly);
        this.allIssuesMetric = allIssuesMetric;
        this.infoIssuesMetric = infoIssuesMetric;
    }

    @Override
    public String toConsoleOutput() {
        if(valueOnly) {
            return "All issues without Info";
        }
        return String.valueOf(allIssuesMetric.getMetricValue() - infoIssuesMetric.getMetricValue());
    }

    @Override
    protected double getMetricValue() {
        return allIssuesMetric.getMetricValue() - infoIssuesMetric.getMetricValue();
    }

    @Override
    protected String getMetricValueDescription() {
        return "All issues without Info";
    }
}
