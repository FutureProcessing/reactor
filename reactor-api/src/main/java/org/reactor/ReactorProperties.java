package org.reactor;

import org.reactor.properties.FilteredProperties;

import java.util.Properties;

public class ReactorProperties extends FilteredProperties {

    private static final String PREFIX = "reactor";

    public ReactorProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }
}
