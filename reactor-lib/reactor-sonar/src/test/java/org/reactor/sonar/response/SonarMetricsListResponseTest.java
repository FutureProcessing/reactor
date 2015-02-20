package org.reactor.sonar.response;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.sonar.wsclient.services.Metric;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.sonar.response.SonarMetricsListResponse.FILTER_DEFAULT;

public class SonarMetricsListResponseTest extends AbstractUnitTest {

    private static final String KEY = "key";
    private static final String DESCRIPTION = "description";
    public static final int ONE_METRIC = 1;

    @Test
    public void shouldPrintToConsoleOutput() {
        // given
        Metric metric = aMetric();
        List<Metric> metrics = newArrayList(metric);
        SonarMetricsListResponse response = new SonarMetricsListResponse(metrics, FILTER_DEFAULT);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).isEqualTo(format("%s. %s - %s\n", ONE_METRIC, KEY, DESCRIPTION));
    }

    private Metric aMetric() {
        Metric metric = new Metric();
        metric.setKey(KEY);
        metric.setDescription(DESCRIPTION);
        return metric;
    }
}