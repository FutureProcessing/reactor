package org.reactor.sonar.response;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public abstract class AbstractMetricValueResponse implements ReactorResponse {

    private boolean valueOnly;

    public AbstractMetricValueResponse(boolean valueOnly) {
        this.valueOnly = valueOnly;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        if (valueOnly) {
            responseRenderer.renderDoubleLine(getMetricValue());
            return;
        }
        responseRenderer.renderTextLine("metric", "%s = %s", getMetricValueDescription(), getMetricValue());
    }

    protected abstract double getMetricValue();

    protected abstract String getMetricValueDescription();
}
