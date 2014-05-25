package org.reactor.annotation;

import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.toArray;
import static java.util.Arrays.asList;
import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import org.apache.commons.cli.ParseException;
import org.reactor.request.ReactorRequest;
import org.reactor.response.CommandHelpResponse;
import org.reactor.response.ExceptionReactorResponse;
import org.reactor.response.ReactorResponse;

public class AnnotatedNestingReactorMethodInvoker {

    private final AbstractAnnotatedNestingReactor nestingReactor;
    private final AnnotatedNestingReactorMethodProxyOptionsProvider optionsProvider;

    private AnnotatedNestingReactorMethodProxyArgumentsProvider objectMethodArgumentsProvider;
    private Method objectMethodToInvoke;

    public AnnotatedNestingReactorMethodInvoker(AbstractAnnotatedNestingReactor nestingReactor,
                                                Predicate<Method> methodFilter,
                                                AnnotatedNestingReactorMethodProxyOptionsProvider optionsProvider) {
        this.nestingReactor = nestingReactor;
        this.optionsProvider = optionsProvider;

        initializeObjectMethodToInvoke(methodFilter);
        initializeObjectMethodArgumentsProvider(optionsProvider);
    }

    private void initializeObjectMethodToInvoke(Predicate<Method> methodFilter) {
        objectMethodToInvoke = find(asList(nestingReactor.getClass().getDeclaredMethods()), methodFilter, null);
        if (objectMethodToInvoke == null) {
            throw new AnnotatedNestingReactorMethodProxyAnnotationMissingException(nestingReactor);
        }
    }

    private void initializeObjectMethodArgumentsProvider(AnnotatedNestingReactorMethodProxyOptionsProvider optionsProvider) {
        objectMethodArgumentsProvider = new AnnotatedNestingReactorMethodProxyArgumentsProvider(
            optionsProvider.provideReactorMethodProxyOptions(objectMethodToInvoke));
    }

    public ReactorResponse invoke(ReactorRequest reactorRequest) {
        try {
            Iterable<Object> methodArguments = objectMethodArgumentsProvider
                .provideReactorMethodProxyArguments(reactorRequest);
            return (ReactorResponse) objectMethodToInvoke
                .invoke(nestingReactor, toArray(methodArguments, Object.class));
        } catch (ParseException e) {
            return new CommandHelpResponse(e.getMessage(), nestingReactor,
                optionsProvider.provideReactorMethodProxyOptions(objectMethodToInvoke));
        } catch (Throwable genericException) {
            return new ExceptionReactorResponse(genericException);
        }
    }

}
