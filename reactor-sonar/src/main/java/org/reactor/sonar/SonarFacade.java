package org.reactor.sonar;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang.StringUtils;
import org.reactor.ReactorProcessingException;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import java.net.URISyntaxException;

public class SonarFacade {
    
    @VisibleForTesting
    static final String UNKNOWN_PRODUCT_KEY = "Unknown project key";
    @VisibleForTesting
    static final String UNKNOWN_METRIC = "Unknown metric";

    private Sonar sonar;

    public static SonarFacade forServerDetails(String serverUrl, String username, String password)
            throws URISyntaxException {
        return new SonarFacade(serverUrl, username, password);
    }
    
    private Sonar getSonarInstance(String serverUrl, String username, String password) {
        Sonar sonar = null;
        if (StringUtils.isEmpty(username)) {
            sonar = Sonar.create(serverUrl);
        } else {
            sonar = Sonar.create(serverUrl, username, password);
        }
        return sonar;
    }

    private SonarFacade(String serverUrl, String username, String password) {
        sonar = getSonarInstance(serverUrl, username, password);
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
