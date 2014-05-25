package org.reactor.annotation;

import org.reactor.Reactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

public class AnnotatedNestingReactorMethodProxyReactor implements Reactor {

    private final String nestedReactorTrigger;
    private final String nestedReactorDescription;
    private AnnotatedNestingReactorMethodInvoker methodInvoker;

    public AnnotatedNestingReactorMethodProxyReactor(AbstractAnnotatedNestingReactor nestingReactor,
                                                     String nestedReactorMethodName, String nestedReactorTrigger,
                                                     String nestedReactorDescription) {
        this.nestedReactorTrigger = nestedReactorTrigger;
        this.nestedReactorDescription = nestedReactorDescription;

        initializeMethodInvoker(nestingReactor, nestedReactorMethodName);
    }

    private void initializeMethodInvoker(AbstractAnnotatedNestingReactor nestingReactor, String nestedReactorMethodName) {
        methodInvoker = new AnnotatedNestingReactorMethodInvoker(nestingReactor,
            new AnnotatedNestingReactorMethodFilter(nestedReactorMethodName),
            new AnnotatedNestingReactorMethodProxyOptionsProvider());
    }

    @Override
    public ReactorResponse react(ReactorRequest request) {
        return methodInvoker.invoke(request);
    }

    @Override
    public String getTriggeringExpression() {
        return nestedReactorTrigger;
    }

    @Override
    public String getDescription() {
        return nestedReactorDescription;
    }

}
