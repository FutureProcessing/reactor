package org.reactor.transport.websockets;

import static java.lang.Integer.parseInt;

import com.google.common.base.Predicate;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

public class WebSocketsTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.websockets";

    public static final String PROPERTY_PORT = PREFIX + ".port";
    public static final String DEFAULT_PORT = "8080";

    public WebSocketsTransportProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

    public int getPortNumber() {
        return parseInt(getProperty(PROPERTY_PORT, DEFAULT_PORT));
    }
}
