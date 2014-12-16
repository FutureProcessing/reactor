package org.reactor.transport.irc;

import static java.lang.System.lineSeparator;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

public class IrcReactorCommandResponseWriterTest extends AbstractUnitTest {

    @Mock
    private org.pircbotx.hooks.types.GenericEvent event;

    @InjectMocks
    private IrcReactorCommandResponseWriter responseWriter;

    @Test
    public void shouldRespondThreeTimesForThreeLineResponse() throws Exception {
        // given
        String responseMessage = "first" + lineSeparator() + "second" + lineSeparator() + "third";
        ReactorResponse response = new StringReactorResponse(responseMessage);

        // when
        response.renderResponse(responseWriter);

        // then
        verify(event, times(3)).respond(anyString());
    }

    @Test
    public void shouldRespondOneTimeForOneLineResponse() throws Exception {
        // given
        String responseMessage = "first second and third in one line";
        ReactorResponse response = new StringReactorResponse(responseMessage);

        // when
        response.renderResponse(responseWriter);

        // then
        verify(event, times(1)).respond(anyString());
    }
}
