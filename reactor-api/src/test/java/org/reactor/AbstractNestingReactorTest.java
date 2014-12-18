package org.reactor;

import static org.fest.assertions.Assertions.assertThat;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.junit.Test;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;

public class AbstractNestingReactorTest extends AbstractUnitTest {

    private static final String SENDER_TEST = "TEST";

    private AbstractNestingReactor reactor;

    @Test
    public void shouldCollectAllAnnotatedMethodsAsNestedReactors() {
        // given
        reactor = givenAnnotatedReactor();

        // when
        List<Reactor> commandsReactors = reactor.subReactors();

        // then
        assertThat(commandsReactors.size()).isEqualTo(4);
    }

    @Test
    public void shouldCallAnnotatedReactorMethod() throws Exception {
        // given
        reactor = givenAnnotatedReactor();

        // when
        ReactorResponse reactorResponse = reactor.react(SENDER_TEST, new ReactorRequestInput(
                "test singleArgument -a test"));

        // then
        ReactorResponseRenderer renderer = new SimpleReactorResponseRenderer();
        Writer stringWriter = new StringWriter();
        reactorResponse.renderResponse(renderer);
        renderer.commit(stringWriter);
        assertThat(stringWriter.toString()).isEqualTo("test");
    }

    @Test
    public void shouldThrowAnExceptionWhenReactOnAnnotationIsMissing() {
        // then
        expectedException.expect(ReactorAnnotationMissingException.class);

        // when
        reactor = givenNotAnnotatedReactor();
    }

    @Test
    public void shouldThrowAnExceptionWhenRegisteringItselfAsNestedReactor() {
        // given
        reactor = givenAnnotatedReactor();

        // then
        expectedException.expect(ReactorInitializationException.class);

        // when
        reactor.registerNestedReactor(reactor);
    }

    private AbstractNestingReactor givenNotAnnotatedReactor() {
        return new TestNotAnnotatedNestingReactor();
    }

    private AbstractNestingReactor givenAnnotatedReactor() {
        return new TestAnnotatedNestingReactor();
    }
}
