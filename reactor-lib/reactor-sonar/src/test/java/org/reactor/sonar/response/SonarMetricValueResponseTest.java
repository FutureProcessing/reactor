package org.reactor.sonar.response;


import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.sonar.data.MetricWithValueResource;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.Resource;

import static org.fest.assertions.Assertions.assertThat;

public class SonarMetricValueResponseTest extends AbstractUnitTest {

    private static final String DESCRIPTION = "description";
    private static final boolean NOT_VALUE_ONLY = false;
    private static final boolean VALUE_ONLY = true;

    @Test
    public void shouldPrintToConsoleOutput() {
        // given
        SonarMetricValueResponse response = new SonarMetricValueResponse(new MetricWithValueResource(aMetric(), new Resource()), NOT_VALUE_ONLY);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).isEqualTo(String.format("%s = %s", DESCRIPTION, 0d));
    }

    @Test
    public void shouldPrintValueOnlyToConsoleOutput() {
        // given
        SonarMetricValueResponse response = new SonarMetricValueResponse(new MetricWithValueResource(aMetric(), new Resource()), VALUE_ONLY);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).isEqualTo(String.valueOf(0d));
    }

    private Metric aMetric() {
        Metric metric = new Metric();
        metric.setDescription(DESCRIPTION);
        return metric;
    }
}