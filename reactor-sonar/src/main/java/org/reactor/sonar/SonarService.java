package org.reactor.sonar;

import org.apache.commons.lang.StringUtils;
import org.reactor.ReactorProcessingException;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import java.net.URISyntaxException;

public class SonarService {
    
    public static final String UNKNOWN_PRODUCT_KEY = "Unknown project key";
    public static final String UNKNOWN_METRIC = "Unknown metric";

    private Sonar sonar;

    public static SonarService forServerDetails(String serverUrl, String username, String password)
            throws URISyntaxException {
        return new SonarService(serverUrl, username, password);
    }
    
    private Sonar createSonarInstance(String serverUrl, String username, String password) {
        Sonar sonar = null;
        if (StringUtils.isEmpty(username)) {
            sonar = Sonar.create(serverUrl);
        } else {
            sonar = Sonar.create(serverUrl, username, password);
        }
        return sonar;
    }

    private SonarService(String serverUrl, String username, String password) {
        sonar = createSonarInstance(serverUrl, username, password);
    }
    
    private Resource findResource(String projectKey, String manualMetricKey) {
        return sonar.find(ResourceQuery.createForMetrics(projectKey, manualMetricKey));
    }

    public String getSingleMetric(String projectKey, String manualMetricKey) {
        Resource resource = findResource(projectKey, manualMetricKey);
        if(resource == null) {
            throw new ReactorProcessingException(String.format(UNKNOWN_PRODUCT_KEY, ": %s", projectKey));
        }
        Double value = resource.getMeasureValue(manualMetricKey);
        if(value == null) {
            throw new ReactorProcessingException(String.format(UNKNOWN_METRIC, ": %s", manualMetricKey));
        }
        return String.valueOf(value);
    }

}
