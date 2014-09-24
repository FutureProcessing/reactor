package org.reactor.jira;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.reactor.jira.JiraReactorProperties.PROPERTY_PASSWORD;
import static org.reactor.jira.JiraReactorProperties.PROPERTY_PROJECT_NAME;
import static org.reactor.jira.JiraReactorProperties.PROPERTY_URL;
import static org.reactor.jira.JiraReactorProperties.PROPERTY_USERNAME;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactor.AbstractUnitTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import java.util.Properties;

@RunWith(JUnitParamsRunner.class)
public class JiraReactorPropertiesTest extends AbstractUnitTest {

    private static final String URL_STRING = "url";
    private static final String USER_NAME_STRING = "name";
    private static final String PASS_STRING = "pass";
    private static final String PROJECT_STRING = "test_project";

    private Object propertiesTestData() {
        return $($(PROPERTY_URL, URL_STRING, URL_STRING), $(PROPERTY_USERNAME, USER_NAME_STRING, USER_NAME_STRING),
            $(PROPERTY_PASSWORD, PASS_STRING, PASS_STRING), $(PROPERTY_PROJECT_NAME, PROJECT_STRING, PROJECT_STRING),
            $("invalid.property", URL_STRING, null));
    }

    @Test
    @Parameters(method = "propertiesTestData")
    public void checkIfSonarReactorPropertiesStoresValuesProperly(String propertyName, String value,
                                                                  String expectedValue) {
        JiraReactorProperties properties = new JiraReactorProperties(new Properties() {

            {
                setProperty(propertyName, value);
            }
        });
        String valueRead = properties.getProperty(propertyName);
        assertThat(valueRead, is(expectedValue));
    }

}
