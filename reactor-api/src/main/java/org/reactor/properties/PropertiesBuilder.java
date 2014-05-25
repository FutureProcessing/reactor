package org.reactor.properties;

import org.reactor.ReactorInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesBuilder {

    private Properties loadedProperties;

    private final static Logger LOG = LoggerFactory.getLogger(PropertiesBuilder.class);

    private PropertiesBuilder() {
        loadedProperties = new Properties();
    }

    public static PropertiesBuilder propertiesBuilder() {
        return new PropertiesBuilder();
    }

    public PropertiesBuilder loadFromResourceStream(String classResourcePath) {
        try {
            InputStream propertiesStream = PropertiesBuilder.class.getClassLoader().getResourceAsStream(classResourcePath);
            if (propertiesStream == null) {
                LOG.error("Unable to load properties from {} classpath resource", classResourcePath);
                return this;
            }
            loadFromStream(propertiesStream);
        } catch (Throwable t) {
            throw new ReactorInitializationException(t);
        }
        return this;
    }


    public PropertiesBuilder loadFromStream(InputStream propertiesStream) {
        try {
            loadedProperties.load(propertiesStream);
        } catch (Throwable t) {
            throw new ReactorInitializationException(t);
        }
        return this;
    }

    public PropertiesBuilder loadFromFile(File propertiesFile) {
        try {
            return loadFromStream(new FileInputStream(propertiesFile));
        } catch (FileNotFoundException e) {
            throw new ReactorInitializationException(e);
        }
    }

    public Properties build() {
        return new Properties(loadedProperties);
    }
}
