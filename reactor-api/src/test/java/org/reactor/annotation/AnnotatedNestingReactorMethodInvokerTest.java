package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.TestAnnotatedNestingReactor.TEST_REACTOR_TRIGGER;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.TestAnnotatedNestingReactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.CommandHelpResponse;
import org.reactor.response.NoResponse;
import org.reactor.response.ReactorResponse;

import java.io.StringWriter;
import java.io.Writer;

public class AnnotatedNestingReactorMethodInvokerTest extends AbstractUnitTest {

    private AnnotatedNestingReactorMethodInvoker methodInvoker;

    @Test
    public void shouldCallMethodWithNoArguments() throws Exception {
        // given
        methodInvoker = givenMethodInvoker("noArguments");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
            "noArguments"));

        // then
        assertThat(reactorResponse).isInstanceOf(NoResponse.class);
    }

    @Test
    public void shouldCallMethodWithSingleArgument() throws Exception {
        // given
        methodInvoker = givenMethodInvoker("singleArgument");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
            "singleArgument", "-a", "first"));

        // then
        Writer stringWriter = new StringWriter();
        reactorResponse.renderResponse(stringWriter);
        assertThat(stringWriter.toString()).isEqualTo("first");
    }

    @Test
    public void shouldCallMethodWithManyArguments() throws Exception {
        // given
        methodInvoker = givenMethodInvoker("manyArguments");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
            "manyArguments", "-a", "first", "-b", "1234", "--argumentC", "yes"));

        // then
        Writer stringWriter = new StringWriter();
        reactorResponse.renderResponse(stringWriter);
        assertThat(stringWriter.toString()).isEqualTo("first - 1234 - true");
    }

    @Test
    public void shouldCallMethodWithManyArgumentsInRandomOrder() throws Exception {
        // given
        methodInvoker = givenMethodInvoker("manyArguments");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
                "manyArguments", "--argumentC", "yes", "-b", "1234", "-a", "first"));

        // then
        Writer stringWriter = new StringWriter();
        reactorResponse.renderResponse(stringWriter);
        assertThat(stringWriter.toString()).isEqualTo("first - 1234 - true");
    }

    @Test
    public void shouldThrowAnExceptionWhenGivenMethodHasNoReactOnAnnotationSet() throws Exception {
        // then
        expectedException.expect(AnnotatedNestingReactorMethodProxyAnnotationMissingException.class);

        // when
        methodInvoker = givenMethodInvoker("noAnnotation");
    }

    @Test
    public void shouldPrintHelpWhenCommandArgumentIsMissing() throws Exception {
        // given
        methodInvoker = givenMethodInvoker("requiredArgument");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
            "requiredArgument"));

        // then
        assertThat(reactorResponse).isInstanceOf(CommandHelpResponse.class);
    }

    @Test
    public void shouldPrintHelpWhenCommandArgumentValueIsMissing() {
        // given
        methodInvoker = givenMethodInvoker("requiredArgument");

        // when
        ReactorResponse reactorResponse = methodInvoker.invoke(new ReactorRequest(TEST_REACTOR_TRIGGER,
            "requiredArgument", "-r"));

        // then
        assertThat(reactorResponse).isInstanceOf(CommandHelpResponse.class);
    }

    private AnnotatedNestingReactorMethodInvoker givenMethodInvoker(String methodName) {
        AnnotatedNestingReactorMethodInvoker methodInvoker = new AnnotatedNestingReactorMethodInvoker(
            givenAnnotatedNestingReactor(), new AnnotatedNestingReactorMethodFilter(methodName),
            new AnnotatedNestingReactorMethodProxyOptionsProvider());
        return methodInvoker;
    }

    private AbstractAnnotatedNestingReactor givenAnnotatedNestingReactor() {
        return new TestAnnotatedNestingReactor();
    }

}
