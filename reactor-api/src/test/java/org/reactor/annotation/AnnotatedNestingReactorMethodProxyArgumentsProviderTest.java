package org.reactor.annotation;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.request.parser.ReactorRequestParameterDefinition;
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
                new ReactorRequestParameterDefinition("firstOption", "f", true, String.class),
                new ReactorRequestParameterDefinition("secondOption", "s", true, String.class),
                new ReactorRequestParameterDefinition("thirdOption", "t", true, String.class)
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
                new ReactorRequestParameterDefinition("requiredOption", "r", true, String.class));
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
                new ReactorRequestParameterDefinition("requiredOption", "r", true, String.class));
        reactorRequest = givenReactorRequest("-r");

        // then
        expectedException.expect(MissingArgumentException.class);

        // when
        argumentsProvider.provideReactorMethodProxyArguments(reactorRequest);
    }

    private ReactorRequest givenEmptyReactorRequest() {
        //return new ReactorRequest(SENDER, TRIGGER, ARGUMENTS_EMPTY);
        return null;
    }

    private ReactorRequest givenReactorRequest(String argumentsString) {
        //return new ReactorRequest(SENDER, TRIGGER, parseArguments(argumentsString));
        return null;
    }

    private AnnotatedNestingReactorMethodProxyArgumentsProvider givenArgumentsProvider(ReactorRequestParameterDefinition... options) {
        return new AnnotatedNestingReactorMethodProxyArgumentsProvider(asList(options));
    }
}
