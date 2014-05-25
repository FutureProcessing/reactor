package org.reactor.transport;

import com.google.common.base.Predicate;

import org.reactor.properties.FilteredProperties;

import java.util.Properties;

public class TransportProperties extends FilteredProperties {

    public static final String PREFIX = "reactor.transport";

    public TransportProperties(Properties properties, Predicate<String> propertyKeyPredicate) {
        super(properties, propertyKeyPredicate);
    }

    public TransportProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

}
