package org.reactor.sonar;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

public class SonarReactorTest extends AbstractUnitTest {

    private static final String RESPONSE_STRING = "response";
    @Mock
    private SonarService sonarService;
    @InjectMocks
    private SonarReactor sonarReactor;

    @Test
    public void shouldSonarSingleMetricReturnStringReactorResponse() {
        // given
        given(sonarService.getSingleMetric(any(String.class), any(String.class))).willReturn(RESPONSE_STRING);
        SonarRequestData sonarRequestData = new SonarRequestData();

        // when
        ReactorResponse response = sonarReactor.getSonarSingleMetric(new ReactorRequest<SonarRequestData>(null, null,
            sonarRequestData));

        // then
        assertThat(response).isInstanceOf(StringReactorResponse.class);
    }

}
