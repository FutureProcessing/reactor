package org.reactor.nesting;

import static com.google.common.base.Objects.firstNonNull;
import static org.reactor.utils.ClassUtils.getFirstGenericParameterType;
import java.lang.reflect.Method;
import org.reactor.AbstractNestingReactor;
import org.reactor.Reactor;
import org.reactor.annotation.ReactOn;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.response.ReactorResponse;

public class NestingReactorMethodProxyReactor implements Reactor {

    private String nestedReactorTrigger;
    private String nestedReactorDescription;

    private Reactor typedReactor;

    public NestingReactorMethodProxyReactor(AbstractNestingReactor nestingReactor, Method nestingReactorMethod) {
        readMethodAnnotation(nestingReactorMethod);
        initializeTypedSubReactor(nestingReactor, nestingReactorMethod);
    }

    private void readMethodAnnotation(Method nestingReactorMethod) {
        ReactOn reactOnAnnotation = nestingReactorMethod.getAnnotation(ReactOn.class);
        nestedReactorTrigger = reactOnAnnotation.value();
        nestedReactorDescription = reactOnAnnotation.description();
    }

    private void initializeTypedSubReactor(AbstractNestingReactor nestingReactor, Method nestedReactorMethod) {
        Class<?> genericParameterType = firstNonNull(getFirstGenericParameterType(nestedReactorMethod), Void.class);
        typedReactor = new MethodProxyTypedReactor<>(nestingReactor, nestedReactorMethod.getName(), nestedReactorTrigger, nestedReactorDescription,
            genericParameterType);
    }

    @Override
    public ReactorResponse react(String sender, String reactorInput) {
        return typedReactor.react(sender, reactorInput);
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        // do nothing
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
