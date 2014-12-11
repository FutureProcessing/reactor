package org.reactor.sonar.resource;

import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;

public class EmptyValueResource extends Resource {

    public static final double DEFAULT_VALUE = 0d;
    public static final String DEFAULT_FORMATTED_VALUE = "- not available -";

    public static EmptyValueResource emptyResource() {
        return new EmptyValueResource();
    }

    private EmptyValueResource() {
    }

    @Override
    public Double getMeasureValue(String metricKey) {
        return DEFAULT_VALUE;
    }

    @Override
    public String getMeasureFormattedValue(String metricKey, String defaultValue) {
        return DEFAULT_FORMATTED_VALUE;
    }

    @Override
    public Measure getMeasure(String metricKey) {
        return super.getMeasure(metricKey);
    }
}
