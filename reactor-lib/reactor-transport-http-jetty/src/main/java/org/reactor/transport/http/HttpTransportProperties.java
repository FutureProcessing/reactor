package org.reactor.transport.http;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

import static java.lang.Integer.parseInt;

public class HttpTransportProperties extends TransportProperties {

    private static final String PREFIX = "reactor.transport.websockets";

    private static final String PROPERTY_PORT = PREFIX + ".port";
    private static final String DEFAULT_PORT = "8080";

    public HttpTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public int getPortNumber() {
        return parseInt(getProperty(PROPERTY_PORT, DEFAULT_PORT));
    }
}
