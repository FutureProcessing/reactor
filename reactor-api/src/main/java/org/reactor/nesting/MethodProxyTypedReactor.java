package org.reactor.nesting;

import org.reactor.AbstractNestingReactor;
import org.reactor.AbstractReactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

public class MethodProxyTypedReactor<T> extends AbstractReactor<T> {

    private final String triggeringExpression;
    private final String description;

    private NestingReactorMethodInvoker methodInvoker;

    public MethodProxyTypedReactor(AbstractNestingReactor nestingReactor, String nestingReactorMethodName,
                                   String triggeringExpression, String description, Class<T> requestDataType) {
        super(requestDataType);
        this.triggeringExpression = triggeringExpression;
        this.description = description;

        initializeMethodInvoker(nestingReactor, nestingReactorMethodName);
    }

    private void initializeMethodInvoker(AbstractNestingReactor nestingReactor, String nestingReactorMethodName) {
        methodInvoker = new NestingReactorMethodInvoker(nestingReactor, nestingReactorMethodName);
    }

    @Override
    protected ReactorResponse doReact(ReactorRequest<T> reactorRequest) {
        return methodInvoker.invoke(reactorRequest);
    }

    @Override
    public String getTriggeringExpression() {
        return triggeringExpression;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
