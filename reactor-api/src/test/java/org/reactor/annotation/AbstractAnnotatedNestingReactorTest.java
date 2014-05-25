package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.request.ReactorRequestParser.parseRequestFromLine;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.Reactor;
import org.reactor.TestAnnotatedNestingReactor;
import org.reactor.TestNotAnnotatedNestingReactor;
import org.reactor.response.ReactorResponse;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class AbstractAnnotatedNestingReactorTest extends AbstractUnitTest {

    public static final String SENDER_TEST = "TEST";
    private AbstractAnnotatedNestingReactor reactor;

    @Test
    public void shouldCollectAllAnnotatedMethodsAsNestedReactors() {
        // given
        reactor = givenAnnotatedReactor();

        // when
        List<Reactor> commandsReactors = reactor.getNestedReactors();

        // then
        assertThat(commandsReactors.size()).isEqualTo(4);
    }

    @Test
    public void shouldCallAnnotatedReactorMethod() throws Exception {
        // given
        reactor = givenAnnotatedReactor();

        // when
        ReactorResponse reactorResponse = reactor.react(parseRequestFromLine(SENDER_TEST,
            "!test singleArgument -a test"));

        // then
        Writer stringWriter = new StringWriter();
        reactorResponse.renderResponse(stringWriter);
        assertThat(stringWriter.toString()).isEqualTo("test");
    }

    @Test
    public void shouldThrowAnExceptionWhenReactOnAnnotationIsMissing() {
        // then
        expectedException.expect(ReactorAnnotationMissingException.class);

        // when
        reactor = givenNotAnnotatedReactor();
    }

    private AbstractAnnotatedNestingReactor givenNotAnnotatedReactor() {
        return new TestNotAnnotatedNestingReactor();
    }

    private AbstractAnnotatedNestingReactor givenAnnotatedReactor() {
        return new TestAnnotatedNestingReactor();
    }
}
