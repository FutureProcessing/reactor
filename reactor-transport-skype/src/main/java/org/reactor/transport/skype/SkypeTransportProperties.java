package org.reactor.transport.skype;

import static com.google.common.base.Splitter.on;

import com.google.common.base.Predicate;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

public class SkypeTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.skype";

    public static final String PROPERTY_APPLICATION_NAME = PREFIX + ".applicationName";
    public static final String PROPERTY_BROADCAST_LIST = PREFIX + ".broadcastList";
    public static final String DEFAULT_VALUE = "";

    public SkypeTransportProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

    public String getApplicationName() {
        return getProperty(PROPERTY_APPLICATION_NAME);
    }

    public Iterable<String> getBroadcastList() {
        return on(",").split(getProperty(PROPERTY_BROADCAST_LIST, DEFAULT_VALUE));
    }
}
