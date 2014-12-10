package org.reactor.filesystem;

import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.parseBoolean;

import java.util.Properties;

import org.reactor.ReactorProperties;

public class FilesystemReactorProperties extends ReactorProperties {

    public static final String PROPERTY_LISTENING_DIRECTORY = "reactor.filesystem.directory";
    public static final String PROPERTY_LISTENER_ENABLED = "reactor.filesystem.listener.enabled";
    public static final String PROPERTY_SYSTEM_TEMPORARY_DIRECTORY = "java.io.tmpdir";

    public FilesystemReactorProperties(Properties properties) {
        super(properties);
    }

    public String getListeningDirectory() {
        return getProperty(PROPERTY_LISTENING_DIRECTORY, System.getProperty(PROPERTY_SYSTEM_TEMPORARY_DIRECTORY));
    }

    public boolean isListenerEnabled() {
        return parseBoolean(getProperty(PROPERTY_LISTENER_ENABLED, TRUE.toString()));
    }
}
