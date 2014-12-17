package org.reactor.transport.irc;

import static java.lang.System.lineSeparator;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.pircbotx.hooks.types.GenericEvent;
import org.reactor.AbstractUnitTest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;

public class IrcReactorCommandResponseWriterTest extends AbstractUnitTest {

    @Mock
    private GenericEvent event;

    @InjectMocks
    private IrcReactorCommandResponseWriter responseWriter;

    @Test
    public void shouldRespondThreeTimesForThreeLineResponse() throws Exception {
        // given
        String responseMessage = "first" + lineSeparator() + "second" + lineSeparator() + "third";
        ReactorResponse response = new StringReactorResponse(responseMessage);
        ReactorResponseRenderer responseRenderer = givenSimpleResponseRenderer();

        // when
        response.renderResponse(responseRenderer);
        responseRenderer.commit(responseWriter);

        // then
        verify(event, times(3)).respond(anyString());
    }

    @Test
    public void shouldRespondOneTimeForOneLineResponse() throws Exception {
        // given
        String responseMessage = "first second and third in one line";
        ReactorResponse response = new StringReactorResponse(responseMessage);
        ReactorResponseRenderer responseRenderer = givenSimpleResponseRenderer();

        // when
        response.renderResponse(responseRenderer);
        responseRenderer.commit(responseWriter);

        // then
        verify(event, times(1)).respond(anyString());
    }

    private SimpleReactorResponseRenderer givenSimpleResponseRenderer() {
        return new SimpleReactorResponseRenderer();
    }
}
