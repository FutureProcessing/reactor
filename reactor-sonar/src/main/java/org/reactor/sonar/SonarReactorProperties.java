package org.reactor.sonar;

import com.google.common.annotations.VisibleForTesting;
import org.reactor.ReactorProperties;
import java.util.Properties;

public class SonarReactorProperties extends ReactorProperties {

    @VisibleForTesting
    static final String PROPERTY_URL = "reactor.sonar.url";
    @VisibleForTesting
    static final String PROPERTY_USERNAME = "reactor.sonar.username";
    @VisibleForTesting
    static final String PROPERTY_PASSWORD = "reactor.sonar.password";

    public SonarReactorProperties(Properties properties) {
        super(properties);
    }

    public String getUrl() {
        return getProperty(PROPERTY_URL);
    }

    public String getUsername() {
        return getProperty(PROPERTY_USERNAME);
    }

    public String getPassword() {
        return getProperty(PROPERTY_PASSWORD);
    }

}
