package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.junit.Test;
import org.reactor.AbstractNestingReactor;
import org.reactor.AbstractUnitTest;
import org.reactor.Reactor;
import org.reactor.ReactorAnnotationMissingException;
import org.reactor.TestAnnotatedNestingReactor;
import org.reactor.TestNotAnnotatedNestingReactor;
import org.reactor.response.ReactorResponse;

public class AbstractNestingReactorTest extends AbstractUnitTest {

    public static final String SENDER_TEST = "TEST";
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
        ReactorResponse reactorResponse = reactor.react(SENDER_TEST,
                "test singleArgument -a test");

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

    private AbstractNestingReactor givenNotAnnotatedReactor() {
        return new TestNotAnnotatedNestingReactor();
    }

    private AbstractNestingReactor givenAnnotatedReactor() {
        return new TestAnnotatedNestingReactor();
    }
}
