package org.reactor.jira;

import com.google.common.annotations.VisibleForTesting;
import org.reactor.ReactorProperties;

import java.util.Properties;

import static java.lang.Integer.parseInt;

public class JiraReactorProperties extends ReactorProperties {

    @VisibleForTesting
    static final String PROPERTY_URL = "reactor.jira.url";
    @VisibleForTesting
    static final String PROPERTY_USERNAME = "reactor.jira.username";
    @VisibleForTesting
    static final String PROPERTY_PASSWORD = "reactor.jira.password";
    @VisibleForTesting
    static final String PROPERTY_PROJECT_NAME = "reactor.jira.projectName";
    @VisibleForTesting
    static final String PROPERTY_BOARD_ID = "reactor.jira.agile.boardId";
    private final static String DEFAULT_PROPERTY_BOARD_ID = "1";

    public JiraReactorProperties(Properties properties) {
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

    public String getProjectName() {
        return getProperty(PROPERTY_PROJECT_NAME);
    }

    public int getAgileBoardId() { return parseInt(getProperty(PROPERTY_BOARD_ID, DEFAULT_PROPERTY_BOARD_ID)); }

}
