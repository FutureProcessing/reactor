package org.reactor.transport;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;

public class TransportControllerTest extends AbstractUnitTest {

    @Mock
    ReactorMessageTransport reactorMessageTransport;

    @InjectMocks
    private TransportController transportController;

    @Test
    public void shouldStopAllTransports() {
        // given
        transportController.addTransport(reactorMessageTransport);
        transportController.addTransport(reactorMessageTransport);
        transportController.addTransport(reactorMessageTransport);

        // when
        transportController.stopTransports();

        // then
        verify(reactorMessageTransport, times(3)).stopTransport();

    }

}
