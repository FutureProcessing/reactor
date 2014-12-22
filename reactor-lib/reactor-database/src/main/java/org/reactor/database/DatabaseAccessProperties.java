package org.reactor.database;

import org.reactor.ReactorProperties;
import org.reactor.properties.FilteredProperties;

import java.io.InputStream;

public class DatabaseAccessProperties extends FilteredProperties {

    private static final String PREFIX = "reactor.database";

    private static final String PROPERTY_CONNECTIONS = PREFIX + ".connections";
    private static final String PROPERTY_CONNECTIONS_DEFAULT = "database-connections.xml";

    public DatabaseAccessProperties(ReactorProperties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public InputStream getConnectionsFileStream() {
        String connectionsFileLocation = getProperty(PROPERTY_CONNECTIONS, PROPERTY_CONNECTIONS_DEFAULT);
        return getClass().getClassLoader().getResourceAsStream(connectionsFileLocation);
    }
}

