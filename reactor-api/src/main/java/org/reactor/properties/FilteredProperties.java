package org.reactor.properties;

import static com.google.common.collect.Iterables.filter;
import com.google.common.base.Predicate;
import java.util.Properties;

public class FilteredProperties extends Properties {

    public FilteredProperties(Properties properties, Predicate<String> propertyKeyPredicate) {
        Iterable<String> filteredKeys = filter(properties.stringPropertyNames(), propertyKeyPredicate);
        for (String filteredKey : filteredKeys) {
            setProperty(filteredKey, properties.getProperty(filteredKey));
        }
    }
}
