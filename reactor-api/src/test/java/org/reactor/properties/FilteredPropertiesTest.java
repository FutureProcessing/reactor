package org.reactor.properties;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.util.Properties;

import static org.fest.assertions.Assertions.assertThat;

public class FilteredPropertiesTest extends AbstractUnitTest {

    public static final String PROPERTY_VALUE = "value";
    private Properties properties;

    @Test
    public void shouldHaveOnlyFilteredProperties() {
        // given
        properties = givenNotFilteredProperties();

        // when
        FilteredProperties filteredProperties = new FilteredProperties(properties,
                propertyKey -> propertyKey.startsWith("a") || propertyKey.startsWith("b"));

        // then
        assertThat(filteredProperties.containsKey("aProperty")).isTrue();
        assertThat(filteredProperties.containsKey("bProperty")).isTrue();
        assertThat(filteredProperties.containsKey("cProperty")).isFalse();
        assertThat(filteredProperties.size()).isEqualTo(2);
    }

    private Properties givenNotFilteredProperties() {
        Properties properties = new Properties();
        properties.setProperty("aProperty", PROPERTY_VALUE);
        properties.setProperty("bProperty", PROPERTY_VALUE);
        properties.setProperty("cProperty", PROPERTY_VALUE);
        return properties;
    }
}
