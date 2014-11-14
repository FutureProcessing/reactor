package org.reactor.transport.speech;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

import static java.lang.Long.parseLong;

public class SpeechTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.speech";

    public static final String PROPERTY_API_KEY = PREFIX + ".apiKey";
    public static final String DEFAULT_API_VALUE = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";

    public static final String PROPERTY_LANGUAGE_KEY = PREFIX + ".language";
    public static final String DEFAULT_LANGUAGE_VALUE = "auto";

    public static final String PROPERTY_MINVOLUME_KEY = PREFIX + ".minVolume";
    public static final String DEFAULT_MINVOLUME_VALUE = "55";

    public static final String PROPERTY_SILENCEDURATION_KEY = PREFIX + ".silenceDuration";
    public static final String DEFAULT_SILENCEDURATION_VALUE = "1";

    public SpeechTransportProperties(Properties properties) {
        super(properties, propertyKeyStartPredicate(PREFIX));
    }

    public String getApiKey() {
        return getProperty(PROPERTY_API_KEY, DEFAULT_API_VALUE);
    }

    public String getLanguage() {
        return getProperty(PROPERTY_LANGUAGE_KEY, DEFAULT_LANGUAGE_VALUE);
    }

    public long getMinimumVolume() {
        return parseLong(getProperty(PROPERTY_MINVOLUME_KEY, DEFAULT_MINVOLUME_VALUE));
    }

    public long getSilenceDuration() {
        return parseLong(getProperty(PROPERTY_SILENCEDURATION_KEY, DEFAULT_SILENCEDURATION_VALUE));
    }
}
