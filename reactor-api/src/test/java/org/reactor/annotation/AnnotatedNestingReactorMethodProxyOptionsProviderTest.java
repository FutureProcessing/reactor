package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.TestAnnotatedNestingReactor;

import java.lang.reflect.Method;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class AnnotatedNestingReactorMethodProxyOptionsProviderTest extends AbstractUnitTest {

    private AnnotatedNestingReactorMethodProxyOptionsProvider optionsProvider;
    private Method method;

    @Test
    public void shouldCollectAllDeclaredOptions() throws NoSuchMethodException {
        // given
        optionsProvider = givenOptionsProvider();
        method = givenAnnotatedMethod();

        // when
        Iterable<ReactorRequestParameterDefinition> options = optionsProvider
            .provideReactorMethodProxyOptions(method);

        // then
        assertThat(options).containsOnly(givenStringOption("argumentA", "a"),
            givenIntOption("argumentB", "b"), givenBooleanOption("argumentC", "c"));
    }

    private ReactorRequestParameterDefinition givenStringOption(String name, String shortName) {
        return givenOption(name, shortName, false, String.class);
    }

    private ReactorRequestParameterDefinition givenBooleanOption(String name, String shortName) {
        return givenOption(name, shortName, false, boolean.class);
    }

    private ReactorRequestParameterDefinition givenIntOption(String name, String shortName) {
        return givenOption(name, shortName, false, int.class);
    }

    private ReactorRequestParameterDefinition givenOption(String name, String shortName, boolean required,
                                                                 Class<?> dataType) {
        return new ReactorRequestParameterDefinition(name, shortName, required, dataType);
    }

    private Method givenAnnotatedMethod() throws NoSuchMethodException {
        return TestAnnotatedNestingReactor.class.getDeclaredMethod("manyArguments", String.class, int.class,
            boolean.class);
    }

    private AnnotatedNestingReactorMethodProxyOptionsProvider givenOptionsProvider() {
        return new AnnotatedNestingReactorMethodProxyOptionsProvider();
    }
}
