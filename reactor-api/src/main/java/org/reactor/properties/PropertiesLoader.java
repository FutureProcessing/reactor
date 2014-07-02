package org.reactor.properties;

import static java.lang.String.format;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.reactor.ReactorInitializationException;

public class PropertiesLoader {

    private String classResourcePath;

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
        Properties loadedProperties = new Properties();
        try {
            loadedProperties.load(propertiesStream);
        } catch (IOException ioe) {
            throw new ReactorInitializationException("Cannot read Properties from stream.", ioe);
        }
        return loadedProperties;
    }
}
