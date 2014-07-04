package org.reactor.sonar;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.ReactorProcessingException;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import java.util.List;

public class SonarServiceTest extends AbstractUnitTest {

    private static String ANY_PROJECT_KEY = "auriga:project";
    private static String ANY_METRIC = "blablabla";
    private static String METRIC_KNOWN = "complexity";
    private static Double COMPLEXITY = 123.0d;
    @Mock
    private Sonar sonar;
    @InjectMocks
    private SonarService sonarFacade;

    @Test
    public void shouldGetSingleMetricThrowExceptionOnInvalidProjectKey() throws Exception {
        // given
        given(sonar.find(any(ResourceQuery.class))).willReturn(null);

        // then
        expectedException.expect(ReactorProcessingException.class);
        expectedException.expectMessage(SonarService.UNKNOWN_PRODUCT_KEY);

        // when
        sonarFacade.getSingleMetric(ANY_PROJECT_KEY, ANY_METRIC);
    }

    @Test
    public void shouldGetSingleMetricThrowExceptionOnInvalidMetric() throws Exception {
        // then
        expectedException.expect(ReactorProcessingException.class);
        expectedException.expectMessage(SonarService.UNKNOWN_METRIC);

        // given
        Resource resource = new Resource();
        resource.setMeasures(null);
        given(sonar.find(any(ResourceQuery.class))).willReturn(resource);

        // when
        sonarFacade.getSingleMetric(ANY_PROJECT_KEY, ANY_METRIC);
    }

    @Test
    public void shouldGetSingleMetricReturnValidResponse() {
        // given
        Resource resource = createResource();
        given(sonar.find(any(ResourceQuery.class))).willReturn(resource);

        // when
        String result = sonarFacade.getSingleMetric(ANY_PROJECT_KEY, METRIC_KNOWN);

        // then
        assertThat(result).isEqualTo(String.valueOf(COMPLEXITY));
    }

    private Resource createResource() {
        Resource resource = new Resource();
        Measure measure = new Measure();
        measure.setMetricKey(METRIC_KNOWN);
        measure.setValue(COMPLEXITY);
        List<Measure> measures = Lists.newArrayList(measure);
        measures.add(measure);
        resource.setMeasures(measures);
        return resource;
    }

}
