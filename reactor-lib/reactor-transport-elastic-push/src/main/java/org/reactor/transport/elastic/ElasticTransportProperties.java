package org.reactor.transport.elastic;

import java.io.InputStream;
import java.util.Properties;

import org.reactor.properties.FilteredProperties;

import static java.lang.Integer.parseInt;

public class ElasticTransportProperties extends FilteredProperties {

    private static final String PREFIX = "reactor.transport.elastic";
    private static final String PROPERTY_HOST = PREFIX + ".host";
    private static final String PROPERTY_PORT = PREFIX + ".port";
    private static final String PROPERTY_CONFIGURATION = PREFIX + ".configuration";

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "9200";

    public ElasticTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public String getHost() {
        return getProperty(PROPERTY_HOST, DEFAULT_HOST);
    }

    public int getPort() {
        return parseInt(getProperty(PROPERTY_PORT, DEFAULT_PORT));
    }

    public InputStream getConfigurationFileStream() {
        String connectionsFileLocation = getProperty(PROPERTY_CONFIGURATION);
        return getClass().getClassLoader().getResourceAsStream(connectionsFileLocation);
    }
}
