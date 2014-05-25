package org.reactor.jenkins;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import org.reactor.ReactorProperties;
import java.util.Properties;

public class JenkinsReactorProperties extends ReactorProperties {

    private static final String PROPERTY_URL = "reactor.jenkins.url";
    private static final String PROPERTY_USERNAME = "reactor.jenkins.username";
    private static final String PROPERTY_PASSWORD = "reactor.jenkins.password";
    private static final String PROPERTY_LISTENER_ENABLED = "reactor.jenkins.listener.enabled";
    private static final String PROPERTY_LISTENER_PORT = "reactor.jenkins.listener.port";
    private static final String DEFAULT_LISTENER_PORT = "9999";

    public JenkinsReactorProperties(Properties properties) {
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

    public boolean isListenerEnabled() {
        return parseBoolean(getProperty(PROPERTY_LISTENER_ENABLED, FALSE.toString()));
    }

    public int getListenerPort() {
        return parseInt(getProperty(PROPERTY_LISTENER_PORT, DEFAULT_LISTENER_PORT));
    }
}
