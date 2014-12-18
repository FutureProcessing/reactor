package org.reactor.properties;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.util.Properties;

public class PropertiesLoaderTest extends AbstractUnitTest {

    public static final String TEST_PROPERTIES = "test-properties.properties";
    public static final String TEST_PROPERTY_KEY = "test_key";
    private static final String TEST_PROPERTY_VALUE = "test_value";

    private PropertiesLoader propertiesLoader;

    @Test
    public void shouldLoadTestProperties() {
        // given
        propertiesLoader = givenLoaderForPropertiesFile(TEST_PROPERTIES);

        // when
        Properties properties = propertiesLoader.load();

        // then
        assertThat(properties.getProperty(TEST_PROPERTY_KEY)).isEqualTo(TEST_PROPERTY_VALUE);
    }

    private PropertiesLoader givenLoaderForPropertiesFile(String propertiesFile) {
        return PropertiesLoader.propertiesLoader().fromResourceStream(propertiesFile);
    }
}
