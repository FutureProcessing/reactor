package org.reactor.annotation;

import org.reactor.AbstractNestingReactor;
import org.reactor.Reactor;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.response.ReactorResponse;

public class AnnotatedNestingReactorMethodProxyReactor implements Reactor {

    private final String nestedReactorTrigger;
    private final String nestedReactorDescription;
    private AnnotatedNestingReactorMethodInvoker methodInvoker;

    public AnnotatedNestingReactorMethodProxyReactor(AbstractNestingReactor nestingReactor,
                                                     String nestedReactorMethodName, String nestedReactorTrigger,
                                                     String nestedReactorDescription) {
        this.nestedReactorTrigger = nestedReactorTrigger;
        this.nestedReactorDescription = nestedReactorDescription;

        initializeMethodInvoker(nestingReactor, nestedReactorMethodName);
    }

    private void initializeMethodInvoker(AbstractNestingReactor nestingReactor, String nestedReactorMethodName) {
        methodInvoker = new AnnotatedNestingReactorMethodInvoker(nestingReactor,
            new AnnotatedNestingReactorMethodFilter(nestedReactorMethodName));
    }

    @Override
    public ReactorResponse react(String sender, String reactorInput) {
        //return methodInvoker.invoke(request);
        return null;
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {

        // TODO
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
