package org.reactor;

import com.google.common.base.Predicate;
import org.reactor.properties.FilteredProperties;
import java.util.Properties;

public class ReactorProperties extends FilteredProperties {

    public static final String PREFIX = "reactor";

    public static final String PROPERTY_REACTOR_IMPLEMENTATION = PREFIX + ".implementation";

    public ReactorProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

    public String getReactorImplementation() {
        return getProperty(PROPERTY_REACTOR_IMPLEMENTATION);
    }
}
