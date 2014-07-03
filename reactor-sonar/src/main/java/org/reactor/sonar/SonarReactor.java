package org.reactor.sonar;

import static org.reactor.sonar.SonarFacade.forServerDetails;
import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import java.net.URISyntaxException;

@ReactOn(value = "sonar", description = "Sonar reactor")
public class SonarReactor extends AbstractNestingReactor {

    private SonarFacade sonarFacade;
    
    protected void createdSonarFacade(String serverUrl, String username, String password) throws URISyntaxException {
        sonarFacade = forServerDetails(serverUrl, username, password);
    }
    
    private void initNestingReactor(SonarReactorProperties reactorProperties) {
        try {
            createdSonarFacade(reactorProperties.getUrl(), reactorProperties.getUsername(),
                reactorProperties.getPassword());
        } catch (URISyntaxException e) {
            throw new ReactorInitializationException(e);
        }
    }
    
    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        initNestingReactor(new SonarReactorProperties(properties));
    }
    
    @ReactOn(value = "singleMetric", description = "Displays a single metric for a project key\n-k")
    public ReactorResponse getSonarSingleMetric(ReactorRequest<SonarRequestData> sonarRequest) {
        String response = sonarFacade.getSingleMetric(sonarRequest.getRequestData().getProjectKey(), sonarRequest.getRequestData().getMetrics());
        return new StringReactorResponse(response);
    }


}
