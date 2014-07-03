package org.reactor.sonar;

import static org.reactor.sonar.SonarReactorProperties.PROPERTY_PASSWORD;
import static org.reactor.sonar.SonarReactorProperties.PROPERTY_USERNAME;
import static org.reactor.sonar.SonarReactorProperties.PROPERTY_URL;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import java.util.Properties;

public class SonarReactorPropertiesTest {

    private static final String URL_STRING = "url";
    private static final String USER_NAME_STRING = "name";
    private static final String PASS_STRING = "pass";

    @Test
    public void shouldSonarPropertiesAcceptOnlyValidPrefix() {
        // given
        SonarReactorProperties properties = new SonarReactorProperties(new Properties() {

            {
                setProperty(PROPERTY_URL, URL_STRING);
                setProperty(PROPERTY_USERNAME, USER_NAME_STRING);
                setProperty(PROPERTY_PASSWORD, PASS_STRING);
                setProperty("invalid.property", "invalid");
            }
        });

        // when
        String propertyValue = properties.getProperty("invalid.property");

        // then
        assertThat(propertyValue).isNull();

        // when
        propertyValue = properties.getProperty(PROPERTY_URL);

        // then
        assertThat(propertyValue).isEqualTo(URL_STRING);

        // when
        propertyValue = properties.getProperty(PROPERTY_USERNAME);

        // then
        assertThat(propertyValue).isEqualTo(USER_NAME_STRING);

        // when
        propertyValue = properties.getProperty(PROPERTY_PASSWORD);

        // then
        assertThat(propertyValue).isEqualTo(PASS_STRING);
    }

}
