package org.reactor.sonar;

import static org.reactor.sonar.SonarService.forServerDetails;

import java.net.URISyntaxException;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.sonar.data.MetricWithValueResource;
import org.reactor.sonar.request.GetSonarPredefinedMetricRequestData;
import org.reactor.sonar.request.GetSonarSingleMetricRequestData;
import org.reactor.sonar.request.ListSonarMetricsRequestData;
import org.reactor.sonar.response.SonarMetricValueResponse;
import org.reactor.sonar.response.SonarMetricsListResponse;
import org.reactor.sonar.response.metric.IssuesMetricResponse;

@ReactOn(value = "sonar", description = "Sonar reactor")
public class SonarReactor extends AbstractNestingReactor {

    private static final String METRIC_KEY_VIOLATIONS = "violations";
    private static final String METRIC_KEY_INFO_VIOLATIONS = "info_violations";
    private static final String METRIC_KEY_RULES_COMLIANCE = "violations_density";
    private static final String METRIC_KEY_TECHNICAL_DEBT = "sqale_index";
    private static final String METRIC_KEY_COVERAGE = "coverage";

    private SonarService sonarService;

    protected void createSonarService(String serverUrl, String username, String password, String project)
            throws URISyntaxException {
        sonarService = forServerDetails(serverUrl, username, password, project);
    }

    private void initNestingReactor(SonarReactorProperties reactorProperties) {
        try {
            createSonarService(reactorProperties.getUrl(), reactorProperties.getUsername(),
                reactorProperties.getPassword(), reactorProperties.getProject());
        } catch (URISyntaxException e) {
            throw new ReactorInitializationException(e);
        }
    }

    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        initNestingReactor(new SonarReactorProperties(properties));
    }

    @ReactOn(value = "issues", description = "Displays information about collected issues")
    public ReactorResponse getIssuesMetric(ReactorRequest<GetSonarPredefinedMetricRequestData> sonarRequest) {
        return new IssuesMetricResponse(sonarService.getSingleMetricValueResource(METRIC_KEY_VIOLATIONS),
            sonarService.getSingleMetricValueResource(METRIC_KEY_INFO_VIOLATIONS), sonarRequest.getRequestData()
                .isValueOnly());
    }

    @ReactOn(value = "rules", description = "Displays information about rules comliance")
    public ReactorResponse getRulesComplianceMetric(ReactorRequest<GetSonarPredefinedMetricRequestData> sonarRequest) {
        return new SonarMetricValueResponse(sonarService.getSingleMetricValueResource(METRIC_KEY_RULES_COMLIANCE),
            sonarRequest.getRequestData().isValueOnly());
    }

    @ReactOn(value = "debt", description = "Displays information about technical debt (in days)")
    public ReactorResponse getTechnicalDebtMetric(ReactorRequest<GetSonarPredefinedMetricRequestData> sonarRequest) {
        return new SonarMetricValueResponse(sonarService.getSingleMetricValueResource(METRIC_KEY_TECHNICAL_DEBT),
            sonarRequest.getRequestData().isValueOnly());
    }

    @ReactOn(value = "coverage", description = "Displays information about tests coverage")
    public ReactorResponse getCoverageMetric(ReactorRequest<GetSonarPredefinedMetricRequestData> sonarRequest) {
        return new SonarMetricValueResponse(sonarService.getSingleMetricValueResource(METRIC_KEY_COVERAGE),
            sonarRequest.getRequestData().isValueOnly());
    }

    @ReactOn(value = "metric", description = "Displays a single metric value")
    public ReactorResponse getSonarSingleMetric(ReactorRequest<GetSonarSingleMetricRequestData> sonarRequest) {
        GetSonarSingleMetricRequestData requestData = sonarRequest.getRequestData();
        MetricWithValueResource metricValueResource = sonarService.getSingleMetricValueResource(requestData
            .getMetricKey());
        return new SonarMetricValueResponse(metricValueResource, requestData.isValueOnly());
    }

    @ReactOn(value = "metrics", description = "Lists all available metrics in sonar instance")
    public ReactorResponse listAllMetrics(ReactorRequest<ListSonarMetricsRequestData> listMetricsRequest) {
        return new SonarMetricsListResponse(sonarService.listAllMetrics(), listMetricsRequest.getRequestData()
            .getMetricNameFilter());
    }

}
