package org.reactor.transport;

import org.reactor.properties.FilteredProperties;

import java.util.Properties;
import java.util.function.Predicate;

public class TransportProperties extends FilteredProperties {

    private static final String PREFIX = "reactor.transport";

    public TransportProperties(Properties properties, Predicate<String> propertyKeyPredicate) {
        super(properties, propertyKeyPredicate);
    }

    public TransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

}
