package org.reactor.sonar;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;
import static org.reactor.sonar.resource.EmptyValueResource.emptyResource;
import static org.sonar.wsclient.services.MetricQuery.all;
import static org.sonar.wsclient.services.MetricQuery.byKey;
import static org.sonar.wsclient.services.ResourceQuery.createForMetrics;
import java.net.URISyntaxException;
import java.util.List;
import org.reactor.ReactorProcessingException;
import org.reactor.sonar.data.MetricWithValueResource;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.Resource;

public class SonarService {
    
    public static final String UNKNOWN_METRIC = "Unknown metric: %s";
    private final String project;

    private Sonar sonar;

    public static SonarService forServerDetails(String serverUrl, String username, String password, String project)
            throws URISyntaxException {
        return new SonarService(serverUrl, username, password, project);
    }
    
    private Sonar createSonarInstance(String serverUrl, String username, String password) {
        if (isNullOrEmpty(username)) {
            return Sonar.create(serverUrl);
        } else {
            return Sonar.create(serverUrl, username, password);
        }
    }

    private SonarService(String serverUrl, String username, String password, String project) {
        this.project = project;
        sonar = createSonarInstance(serverUrl, username, password);
    }
    
    private Resource findResource(String manualMetricKey) {
        return sonar.find(createForMetrics(project, manualMetricKey).setAllDepths());
    }

    private Metric findMetric(String manualMetricKey) {
        return sonar.find(byKey(manualMetricKey));
    }

    public MetricWithValueResource getSingleMetricValueResource(String manualMetricKey) {
        Metric metric = findMetric(manualMetricKey);
        if (metric == null) {
            throw new ReactorProcessingException(format(UNKNOWN_METRIC, manualMetricKey));
        }
        Resource resource = findResource(manualMetricKey);
        return new MetricWithValueResource(metric, firstNonNull(resource, emptyResource()));
    }

    public List<Metric> listAllMetrics() {
        return sonar.findAll(all());
    }
}
