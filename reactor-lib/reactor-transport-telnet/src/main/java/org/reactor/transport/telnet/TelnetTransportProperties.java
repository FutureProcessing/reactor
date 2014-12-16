package org.reactor.transport.telnet;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

import static java.lang.Integer.parseInt;

public class TelnetTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.telnet";

    public static final String PROPERTY_PORT = PREFIX + ".port";
    public static final String DEFAULT_PORT = "9999";

    public TelnetTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public int getPortNumber() {
        // TODO think about it ...
        return parseInt(getProperty(PROPERTY_PORT, DEFAULT_PORT));
    }
}
