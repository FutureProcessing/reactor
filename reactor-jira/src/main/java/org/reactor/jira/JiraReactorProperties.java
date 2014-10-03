package org.reactor.jira;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.Integer.parseInt;

import java.util.Locale;
import java.util.Properties;

import org.reactor.ReactorProperties;

public class JiraReactorProperties extends ReactorProperties {

    private static final String PROPERTY_URL = "reactor.jira.url";
    private static final String PROPERTY_USERNAME = "reactor.jira.username";
    private static final String PROPERTY_PASSWORD = "reactor.jira.password";
    private static final String PROPERTY_PROJECT_NAME = "reactor.jira.projectName";
    private static final String PROPERTY_BOARD_ID = "reactor.jira.agile.boardId";
    private static final String PROPERTY_SERVER_LOCALE = "reactor.jira.locale";

    private final static String DEFAULT_PROPERTY_BOARD_ID = "1";
    private final static String DEFAULT_PROPERTY_SERVER_LOCALE = "en_GB";

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

    public Locale getServerLocale() {
        return new Locale(getProperty(PROPERTY_SERVER_LOCALE, DEFAULT_PROPERTY_SERVER_LOCALE));
    }

    public int getAgileBoardId() {
        return parseInt(getProperty(PROPERTY_BOARD_ID, DEFAULT_PROPERTY_BOARD_ID));
    }

}
