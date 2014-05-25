package org.reactor.properties;

import static org.fest.assertions.Assertions.assertThat;

import com.google.common.base.Predicate;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.util.Properties;
import org.reactor.properties.FilteredProperties;

public class FilteredPropertiesTest extends AbstractUnitTest {

    public static final String PROPERTY_VALUE = "value";
    private Properties properties;

    @Test
    public void shouldHaveOnlyFilteredProperties() {
        // given
        properties = givenNotFilteredProperties();

        // when
        FilteredProperties filteredProperties = new FilteredProperties(properties, new Predicate<String>() {
            @Override
            public boolean apply(String propertyName) {
                return propertyName.startsWith("a") || propertyName.startsWith("b");
            }
        });

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
