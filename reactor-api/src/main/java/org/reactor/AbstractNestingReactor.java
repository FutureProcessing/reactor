package org.reactor;

import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.request.ReactorRequestParser.parseNestedRequest;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import java.lang.reflect.Method;
import java.util.List;
import org.reactor.annotation.AnnotatedNestingReactorMethodProxyReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNestingReactor extends AbstractReactor<String> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractNestingReactor.class);

    private List<Reactor> subReactors = newArrayList();

    public AbstractNestingReactor() {
        super(String.class);

        registerAnnotatedNestedReactors();
    }

    private void registerAnnotatedNestedReactors() {
        Method[] reactorMethods = this.getClass().getMethods();
        for (Method reactorMethod : reactorMethods) {
            if (!reactorMethod.isAnnotationPresent(ReactOn.class)) {
                continue;
            }
            Reactor methodProxyReactor = createMethodProxyReactor(reactorMethod);
            registerNestedReactor(methodProxyReactor);
        }
    }

    private Reactor createMethodProxyReactor(Method annotatedNestingReactorMethod) {
        Class<?> returnType = annotatedNestingReactorMethod.getReturnType();
        if (!ReactorResponse.class.isAssignableFrom(returnType)) {
            LOG.warn("Wrong method return type: {}, should be an instance of {} class.", returnType.getName(),
                ReactorResponse.class.getName());
            return null;
        }
        ReactOn reactorAnnotation = annotatedNestingReactorMethod.getAnnotation(ReactOn.class);
        return new AnnotatedNestingReactorMethodProxyReactor(this, annotatedNestingReactorMethod.getName(),
            reactorAnnotation.value(), reactorAnnotation.description());

    }

    public final ReactorResponse doReact(ReactorRequest<String> reactorRequest) {
        ReactorRequest<String> nestedRequest = parseNestedRequest(reactorRequest);
        for (Reactor nestedReactor : subReactors) {
            if (nestedRequest.triggerMatches(nestedReactor.getTriggeringExpression())) {
                return nestedReactor.react(reactorRequest.getSender(), nestedRequest.getRequestData());
            }
        }
        LOG.debug("Unable to find nested reactor for trigger {}", nestedRequest.getTrigger());
        return NO_RESPONSE;
    }

    protected final void registerNestedReactor(Reactor nestedReactor) {
        validateNewNestedReactor(nestedReactor);
        subReactors.add(nestedReactor);
    }

    private void validateNewNestedReactor(Reactor nestedReactor) {
        if (this.equals(nestedReactor)) {
            throw new ReactorInitializationException("Can not register itself as nested reactor!");
        }
    }

    public List<Reactor> subReactors() {
        return subReactors;
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        for (Reactor subReactor : subReactors) {
            topologyVisitor.visitSubReactor(subReactor);
        }
    }
}
