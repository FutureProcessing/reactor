package org.reactor.sonar.response;

import static java.lang.String.format;
import java.io.PrintWriter;
import org.reactor.response.StringReactorResponse;

public abstract class AbstractMetricValueResponse extends StringReactorResponse {

    private boolean valueOnly;

    public AbstractMetricValueResponse(boolean valueOnly) {
        this.valueOnly = valueOnly;
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        if (valueOnly) {
            printWriter.print(getMetricValue());
            return;
        }
        printWriter.print(format("%s = %s", getMetricValueDescription(), getMetricValue()));
    }

    protected abstract double getMetricValue();

    protected abstract String getMetricValueDescription();
}
