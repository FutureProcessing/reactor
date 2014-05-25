package org.reactor.transport.speech;

import static java.lang.Integer.parseInt;

import com.google.common.base.Predicate;

import org.reactor.transport.TransportProperties;

import java.util.Properties;

public class SpeechTransportProperties extends TransportProperties {

    public static final String PREFIX = "reactor.transport.speech";

    public static final String PROPERTY_API_KEY = PREFIX + ".apiKey";
    public static final String DEFAULT_API_VALUE = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";

    public static final String PROPERTY_LANGUAGE_KEY = PREFIX + ".language";
    public static final String DEFAULT_LANGUAGE_VALUE = "auto";

    public static final String PROPERTY_SAMPLERATE_KEY = PREFIX + ".sampleRate";
    public static final String DEFAULT_SAMPLERATE_VALUE = "44100";

    public SpeechTransportProperties(Properties properties) {
        super(properties, new Predicate<String>() {

            public boolean apply(String propertyKey) {
                return propertyKey.startsWith(PREFIX);
            }
        });
    }

    public String getApiKey() {
        return getProperty(PROPERTY_API_KEY, DEFAULT_API_VALUE);
    }

    public String getLanguage() {
        return getProperty(PROPERTY_LANGUAGE_KEY, DEFAULT_LANGUAGE_VALUE);
    }

    public int getSampleRate() {
        return parseInt(getProperty(PROPERTY_SAMPLERATE_KEY, DEFAULT_SAMPLERATE_VALUE));
    }
}
