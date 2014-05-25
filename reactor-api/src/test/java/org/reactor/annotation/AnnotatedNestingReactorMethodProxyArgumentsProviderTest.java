package org.reactor.annotation;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.request.ArgumentsParser.parseArguments;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.annotation.AnnotatedNestingReactorMethodProxyArgumentsProvider;
import org.reactor.annotation.AnnotatedNestingReactorMethodProxyOption;
import org.reactor.request.ReactorRequest;

public class AnnotatedNestingReactorMethodProxyArgumentsProviderTest extends AbstractUnitTest {

    public static final String SENDER = "sender";
    public static final String TRIGGER = "trigger";
    public static final String ARGUMENTS_EMPTY = "";

    private AnnotatedNestingReactorMethodProxyArgumentsProvider argumentsProvider;
    private ReactorRequest reactorRequest;

    @Test
    public void shouldReturnListOfArgumentsWhenGivenRequestHasAllRequiredOptionsPassed() throws ParseException {
        // given
        argumentsProvider = givenArgumentsProvider(
                new AnnotatedNestingReactorMethodProxyOption("firstOption", "f", true, String.class),
                new AnnotatedNestingReactorMethodProxyOption("secondOption", "s", true, String.class),
                new AnnotatedNestingReactorMethodProxyOption("thirdOption", "t", true, String.class)
        );
        reactorRequest = givenReactorRequest("-f one -s two -t three");

        // when
        Iterable<Object> arguments = argumentsProvider.provideReactorMethodProxyArguments(reactorRequest);

        // then
        assertThat(arguments).containsOnly("one", "two", "three");
    }

    @Test
    public void shouldThrowAnExceptionWhenRequiredOptionIsMissing() throws ParseException {
        // given
        argumentsProvider = givenArgumentsProvider(
                new AnnotatedNestingReactorMethodProxyOption("requiredOption", "r", true, String.class));
        reactorRequest = givenEmptyReactorRequest();

        // then
        expectedException.expect(MissingOptionException.class);

        // when
        argumentsProvider.provideReactorMethodProxyArguments(reactorRequest);
    }

    @Test
    public void shouldThrowAnExceptionWhenRequiredOptionIsGivenButValueIsMissing() throws ParseException {
        // given
        argumentsProvider = givenArgumentsProvider(
                new AnnotatedNestingReactorMethodProxyOption("requiredOption", "r", true, String.class));
        reactorRequest = givenReactorRequest("-r");

        // then
        expectedException.expect(MissingArgumentException.class);

        // when
        argumentsProvider.provideReactorMethodProxyArguments(reactorRequest);
    }

    private ReactorRequest givenEmptyReactorRequest() {
        return new ReactorRequest(SENDER, TRIGGER, ARGUMENTS_EMPTY);
    }

    private ReactorRequest givenReactorRequest(String argumentsString) {
        return new ReactorRequest(SENDER, TRIGGER, parseArguments(argumentsString));
    }

    private AnnotatedNestingReactorMethodProxyArgumentsProvider givenArgumentsProvider(AnnotatedNestingReactorMethodProxyOption... options) {
        return new AnnotatedNestingReactorMethodProxyArgumentsProvider(asList(options));
    }
}
