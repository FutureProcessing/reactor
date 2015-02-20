package org.reactor.sonar.response;

import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.sonar.data.MetricWithValueResource;

public class SonarMetricValueResponse extends AbstractMetricValueResponse {

    private final MetricWithValueResource metricValueResource;

    public SonarMetricValueResponse(MetricWithValueResource metricValueResource, boolean valueOnly) {
        super(valueOnly);
        this.metricValueResource = metricValueResource;
    }

    @Override
    public String toConsoleOutput() {
        if (valueOnly) {
            return String.valueOf(metricValueResource.getMetricValue());
        }
        return String.format("%s = %s", getMetricValueDescription(), getMetricValue());
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        if (valueOnly) {
            responseRenderer.renderDoubleLine(getMetricValue());
            return;
        }
        responseRenderer.renderTextLine("metric", "%s = %s", getMetricValueDescription(), getMetricValue());
    }

    @Override
    protected double getMetricValue() {
        return metricValueResource.getMetricValue();
    }

    @Override
    protected String getMetricValueDescription() {
        return metricValueResource.getMetricDescription();
    }
}
