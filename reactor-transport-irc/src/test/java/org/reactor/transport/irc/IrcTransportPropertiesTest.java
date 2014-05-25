package org.reactor.transport.irc;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.transport.TransportProperties;

import java.util.Properties;

public class IrcTransportPropertiesTest extends AbstractUnitTest {

    @Test
    public void shouldIncludeOnlyPropertiesStartingWithPrefix() {
        // given
        TransportProperties properties = new IrcTransportProperties(new Properties() {

            {
                setProperty("reactor.transport.irc.one", "1");
                setProperty("reactor.transport.irc.two", "2");
                setProperty("reactor.transport.irc.three", "3");
                setProperty("invalid.property", "4");
            }
        });

        // when
        String propertyValue = properties.getProperty("invalid.property");

        // then
        assertThat(propertyValue).isNull();
    }
}
