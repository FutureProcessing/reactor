package org.reactor.properties;

import org.reactor.ReactorInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class PropertiesLoader {

    private String classResourcePath;

    private final static Logger LOG = LoggerFactory.getLogger(PropertiesLoader.class);

    private PropertiesLoader() {
    }

    public static PropertiesLoader propertiesLoader() {
        return new PropertiesLoader();
    }

    public PropertiesLoader fromResourceStream(String classResourcePath) {
        this.classResourcePath = classResourcePath;
        return this;
    }


    public Properties load() {
        InputStream propertiesStream = getPropertiesStream();
        return loadPropertiesFromStream(propertiesStream);
    }

    private InputStream getPropertiesStream() {
        InputStream propertiesStream = PropertiesLoader.class.getClassLoader().getResourceAsStream(classResourcePath);
        if (propertiesStream == null) {
            throw new ReactorInitializationException(format("Unable to load Properties from %s classpath resource.", classResourcePath));
        }
        return propertiesStream;
    }

    private Properties loadPropertiesFromStream(InputStream propertiesStream) {
        Properties loadedProperties = null;
        try {
            loadedProperties.load(propertiesStream);
        } catch (IOException ioe) {
            throw new ReactorInitializationException("Cannot read Properties from stream.", ioe);
        }
        return loadedProperties;
    }
}
