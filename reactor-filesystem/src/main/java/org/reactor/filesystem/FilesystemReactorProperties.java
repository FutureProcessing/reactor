package org.reactor.filesystem;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.parseBoolean;

import org.reactor.ReactorProperties;

import java.util.Properties;

public class FilesystemReactorProperties extends ReactorProperties {

    public static final String PROPERTY_LISTENING_DIRECTORY = "reactor.filesystem.directory";
    public static final String PROPERTY_LISTENER_ENABLED = "reactor.filesystem.listener.enabled";

    public FilesystemReactorProperties(Properties properties) {
        super(properties);
    }

    public String getListeningDirectory() {
        return getProperty(PROPERTY_LISTENING_DIRECTORY);
    }

    public boolean isListenerEnabled() {
        return parseBoolean(getProperty(PROPERTY_LISTENER_ENABLED, TRUE.toString()));
    }
}
