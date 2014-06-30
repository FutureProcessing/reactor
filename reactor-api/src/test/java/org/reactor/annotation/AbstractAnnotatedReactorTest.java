package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.TestAnnotatedReactor.ANNOTATED_REACTOR_DESCRIPTION;
import static org.reactor.TestAnnotatedReactor.ANNOTATED_REACTOR_TRIGGER;
import java.io.StringWriter;
import org.junit.Test;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.AbstractUnitTest;
import org.reactor.ReactorAnnotationMissingException;
import org.reactor.TestAnnotatedReactor;
import org.reactor.TestNotAnnotatedReactor;
import org.reactor.response.ReactorResponse;

public class AbstractAnnotatedReactorTest extends AbstractUnitTest {

    public static final String SENDER = "sender";

    private AbstractAnnotatedReactor reactor;

    @Test
    public void shouldThrowAnExceptionWhenReactOnAnnotationIsMissing() {
        // then
        expectedException.expect(ReactorAnnotationMissingException.class);

        // when
        reactor = givenNotAnnotatedReactor();
    }

    @Test
    public void shouldParseReactorAnnotation() {
        // given
        reactor = givenAnnotatedReactor();

        // then
        assertThat(reactor.getTriggeringExpression()).isEqualTo(ANNOTATED_REACTOR_TRIGGER);
        assertThat(reactor.getDescription()).isEqualTo(ANNOTATED_REACTOR_DESCRIPTION);
    }

    @Test
    public void shouldInvokeReactorAndReturnAwaitedResult() throws Exception {
        // given
        reactor = givenAnnotatedReactor();

        // when
        StringWriter writer = new StringWriter();
        ReactorResponse response = reactor.react(SENDER, "one two three");
        response.renderResponse(writer);

        // then
        assertThat(writer.toString()).isEqualTo("one two three");
    }

    private AbstractAnnotatedReactor givenNotAnnotatedReactor() {
        return new TestNotAnnotatedReactor();
    }

    private AbstractAnnotatedReactor givenAnnotatedReactor() {
        return new TestAnnotatedReactor();
    }
}
