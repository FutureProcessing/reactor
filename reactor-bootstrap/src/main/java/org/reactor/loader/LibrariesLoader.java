package org.reactor.loader;

import static com.google.common.base.Optional.fromNullable;
import static java.lang.System.getProperty;

import java.util.ServiceLoader;

import org.reactor.Reactor;
import org.reactor.transport.ReactorMessageTransport;

public class LibrariesLoader {

    private static final String PROPERTY_LIBRARIES_LOCATION = "libraries.location";
    private static final String DEFAULT_LIBRARIES_LOCATION = "../lib";

    public static ServiceLoader<Reactor> loadReactors() {
        return load(Reactor.class);
    }

    public static ServiceLoader<Reactor> loadReactors(String reactorsLocation) {
        return load(Reactor.class, reactorsLocation);
    }

    public static ServiceLoader<ReactorMessageTransport> loadTransports() {
        return load(ReactorMessageTransport.class);
    }

    public static ServiceLoader<ReactorMessageTransport> loadTransports(String transportsLocation) {
        return load(ReactorMessageTransport.class, transportsLocation);
    }

    public static <S> ServiceLoader<S> load(Class<S> clazz) {
        return load(clazz, fromNullable(getProperty(PROPERTY_LIBRARIES_LOCATION)).or(DEFAULT_LIBRARIES_LOCATION));
    }

    public static <S> ServiceLoader<S> load(Class<S> clazz, String location) {
        return ServiceLoader.load(clazz, new DynamicClassLoader(location));
    }
}
