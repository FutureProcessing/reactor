package org.reactor.transport.skype;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

import static com.google.common.base.Splitter.on;

public class SkypeTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.skype";

    public static final String PROPERTY_APPLICATION_NAME = PREFIX + ".applicationName";
    public static final String PROPERTY_BROADCAST_LIST = PREFIX + ".broadcastList";
    public static final String DEFAULT_VALUE = "";

    public SkypeTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public String getApplicationName() {
        return getProperty(PROPERTY_APPLICATION_NAME);
    }

    public Iterable<String> getBroadcastList() {
        return on(",").split(getProperty(PROPERTY_BROADCAST_LIST, DEFAULT_VALUE));
    }
}
