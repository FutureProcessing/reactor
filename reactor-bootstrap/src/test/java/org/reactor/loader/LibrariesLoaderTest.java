package org.reactor.loader;

import static java.lang.ClassLoader.getSystemResource;
import static org.fest.assertions.Assertions.assertThat;

import java.util.ServiceLoader;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.Reactor;
import org.reactor.transport.ReactorMessageTransport;

public class LibrariesLoaderTest extends AbstractUnitTest {

    private static final String REACTOR_DUMMY_JAR = "reactor-dummy.jar";

    @Test
    public void shouldLoadReactorFromExternalJar() {
        // when
        ServiceLoader<Reactor> reactors = LibrariesLoader.loadReactors(resource(REACTOR_DUMMY_JAR));

        // then
        assertThat(reactors).isNotEmpty();
    }

    @Test
    public void shouldLoadReactorsFromExternalJarsDirectory() {
        // when
        ServiceLoader<Reactor> reactors = LibrariesLoader.loadReactors(resources());

        // then
        assertThat(reactors).isNotEmpty();
    }

    @Test
    public void shouldNotLoadNonExistingTransportsFromExternalJar() {
        // when
        ServiceLoader<ReactorMessageTransport> transports = LibrariesLoader.loadTransports(resource(REACTOR_DUMMY_JAR));

        // then
        assertThat(transports).isEmpty();
    }

    private String resources() {
        return resource("");
    }

    private String resource(String name) {
        return getSystemResource(name).getPath();
    }
}
