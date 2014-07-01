package org.reactor.transport;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.response.ReactorResponse;

public class TransportControllerTest extends AbstractUnitTest {

    @Mock
    ReactorMessageTransport runningTransport;
    @Mock
    ReactorMessageTransport notRunningTransport;

    @Mock
    ReactorResponse reactorResponse;

    @InjectMocks
    private TransportController transportController;

    @Test
    public void shouldStopAllTransports() {
        // given
        givenTransportControllerWithTwoRunningTransportsAndOneNotRunning();

        // when
        transportController.stopTransports();

        // then
        verify(runningTransport, times(2)).stopTransport();
    }

    @Test
    public void shouldBroadcastTransportsIfRunning() {
        // given
        givenTransportControllerWithTwoRunningTransportsAndOneNotRunning();

        // when
        transportController.broadcast(reactorResponse);

        // then
        verify(runningTransport, times(2)).broadcast(reactorResponse);
        verify(notRunningTransport, never()).broadcast(reactorResponse);
    }

    private void givenTransportControllerWithTwoRunningTransportsAndOneNotRunning() {
        given(notRunningTransport.isRunning()).willReturn(false);
        given(runningTransport.isRunning()).willReturn(true);

        transportController.addTransport(runningTransport);
        transportController.addTransport(runningTransport);
        transportController.addTransport(notRunningTransport);
    }

}
