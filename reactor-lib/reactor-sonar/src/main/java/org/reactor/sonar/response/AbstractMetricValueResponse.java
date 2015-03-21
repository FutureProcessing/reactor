package org.reactor.sonar.response;

import org.reactor.annotation.ToBeDeleted;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public abstract class AbstractMetricValueResponse implements ReactorResponse {

    protected boolean valueOnly;

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

    @ToBeDeleted
    protected abstract double getMetricValue();

    @ToBeDeleted
    protected abstract String getMetricValueDescription();
}
