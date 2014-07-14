package org.reactor.sonar;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.reactor.sonar.data.MetricWithValueResource;
import org.reactor.sonar.request.GetSonarSingleMetricRequestData;

public class SonarReactorTest extends AbstractUnitTest {

    private static final double RESPONSE_DOUBLE = 123;
    private static final String METRIC = "metric";

    @Mock
    private MetricWithValueResource metricWithValueResource;
    @Mock
    private GetSonarSingleMetricRequestData sonarRequestData;
    @Mock
    private SonarService sonarService;
    @InjectMocks
    private SonarReactor sonarReactor;

    @Test
    public void shouldSonarSingleMetricReturnStringReactorResponse() {
        // given
        givenSonarMetricValueResource();
        givenSonarRequestData();

        // when
        ReactorResponse response = sonarReactor.getSonarSingleMetric(new ReactorRequest<>(null, null,
                sonarRequestData));

        // then
        assertThat(response).isInstanceOf(StringReactorResponse.class);
    }

    private void givenSonarMetricValueResource() {
        given(metricWithValueResource.getMetricValue()).willReturn(RESPONSE_DOUBLE);
        given(sonarService.getSingleMetricValueResource(METRIC)).willReturn(metricWithValueResource);
    }

    private void givenSonarRequestData() {
        given(sonarRequestData.getMetricKey()).willReturn(METRIC);
    }

}
