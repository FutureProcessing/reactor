package org.reactor.sonar;

import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.reactor.sonar.SonarService.UNKNOWN_METRIC;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.ReactorProcessingException;
import org.reactor.sonar.data.MetricWithValueResource;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

public class SonarServiceTest extends AbstractUnitTest {

    private static final String ANY_METRIC = "blablabla";
    private static final String METRIC_KNOWN = "complexity";
    private static final double COMPLEXITY = 123.0d;

    @Mock
    private Metric metric;
    @Mock
    private Resource resource;
    @Mock
    private Sonar sonar;
    @InjectMocks
    private SonarService sonarService;

    @Test
    public void shouldGetSingleMetricThrowExceptionOnInvalidMetric() throws Exception {
        // then
        expectedException.expect(ReactorProcessingException.class);
        expectedException.expectMessage(format(UNKNOWN_METRIC, ANY_METRIC));

        // given
        givenInvalidMetric();

        // when
        sonarService.getSingleMetricValueResource(ANY_METRIC);
    }

    @Test
    public void shouldGetSingleMetricReturnValidResponse() {
        // given
        givenResource();
        givenMetric();

        // when
        MetricWithValueResource result = sonarService.getSingleMetricValueResource(METRIC_KNOWN);

        // then
        assertThat(result.getMetricValue()).isEqualTo(COMPLEXITY);
    }

    private void givenMetric() {
        given(metric.getKey()).willReturn(METRIC_KNOWN);
        given(sonar.find(isA(MetricQuery.class))).willReturn(metric);
    }

    private void givenResource() {
        given(resource.getMeasureValue(METRIC_KNOWN)).willReturn(COMPLEXITY);
        given(sonar.find(isA(ResourceQuery.class))).willReturn(resource);
    }

    private void givenInvalidMetric() {
        given(sonar.find(any(MetricQuery.class))).willReturn(null);
    }
}
