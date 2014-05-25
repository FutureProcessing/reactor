package org.reactor.annotation;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.TestAnnotatedNestingReactor;

import java.lang.reflect.Method;

public class AnnotatedNestingReactorMethodProxyOptionsProviderTest extends AbstractUnitTest {

    private AnnotatedNestingReactorMethodProxyOptionsProvider optionsProvider;
    private Method method;

    @Test
    public void shouldCollectAllDeclaredOptions() throws NoSuchMethodException {
        // given
        optionsProvider = givenOptionsProvider();
        method = givenAnnotatedMethod();

        // when
        Iterable<AnnotatedNestingReactorMethodProxyOption> options = optionsProvider
            .provideReactorMethodProxyOptions(method);

        // then
        assertThat(options).containsOnly(givenStringOption("argumentA", "a"),
            givenIntOption("argumentB", "b"), givenBooleanOption("argumentC", "c"));
    }

    private AnnotatedNestingReactorMethodProxyOption givenStringOption(String name, String shortName) {
        return givenOption(name, shortName, false, String.class);
    }

    private AnnotatedNestingReactorMethodProxyOption givenBooleanOption(String name, String shortName) {
        return givenOption(name, shortName, false, boolean.class);
    }

    private AnnotatedNestingReactorMethodProxyOption givenIntOption(String name, String shortName) {
        return givenOption(name, shortName, false, int.class);
    }

    private AnnotatedNestingReactorMethodProxyOption givenOption(String name, String shortName, boolean required,
                                                                 Class<?> dataType) {
        return new AnnotatedNestingReactorMethodProxyOption(name, shortName, required, dataType);
    }

    private Method givenAnnotatedMethod() throws NoSuchMethodException {
        return TestAnnotatedNestingReactor.class.getDeclaredMethod("manyArguments", String.class, int.class,
            boolean.class);
    }

    private AnnotatedNestingReactorMethodProxyOptionsProvider givenOptionsProvider() {
        return new AnnotatedNestingReactorMethodProxyOptionsProvider();
    }
}
