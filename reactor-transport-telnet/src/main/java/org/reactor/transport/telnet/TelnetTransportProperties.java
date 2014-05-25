package org.reactor.transport.telnet;

import static java.lang.Integer.parseInt;

import com.google.common.base.Predicate;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

public class TelnetTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.telnet";

    public static final String PROPERTY_PORT = PREFIX + ".port";
    public static final String DEFAULT_PORT = "9999";

    public TelnetTransportProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

    public int getPortNumber() {
        // TODO think about it ...
        return parseInt(getProperty(PROPERTY_PORT, DEFAULT_PORT));
    }
}
