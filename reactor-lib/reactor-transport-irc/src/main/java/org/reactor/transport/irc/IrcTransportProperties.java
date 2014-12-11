package org.reactor.transport.irc;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Iterables.toArray;

public class IrcTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.irc";

    public static final String PROPERTY_SERVER_HOST = PREFIX + ".server";
    public static final String PROPERTY_JOIN_CHANNELS = PREFIX + ".channels";
    public static final String PROPERTY_NICKNAME = PREFIX + ".nickname";

    public static final String DEFAULT_JOIN_CHANNELS = "";
    public static final char SEPARATOR = ',';

    public IrcTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public String getServerHost() {
        return getProperty(PROPERTY_SERVER_HOST);
    }

    public String[] getJoinChannels() {
        return toArray(on(SEPARATOR)
                .split(getProperty(PROPERTY_JOIN_CHANNELS, DEFAULT_JOIN_CHANNELS)), String.class);
    }

    public String getNickname() {
        return getProperty(PROPERTY_NICKNAME);
    }
}
