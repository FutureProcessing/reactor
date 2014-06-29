package org.reactor;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.reactor.request.ReactorRequestInput.TRIGGER_MATCHES;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.lang.reflect.Method;
import java.util.List;
import org.reactor.annotation.ReactOn;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.nesting.NestingReactorMethodProxyReactor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNestingReactor extends AbstractAnnotatedReactor<String> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractNestingReactor.class);

    private final static Predicate<Method> REACTOR_METHOD = new Predicate<Method>() {

        @Override
        public boolean apply(Method method) {
            return annotationPresent(method) && returnTypeMatches(method) && parametersMatch(method);
        }

        private boolean parametersMatch(Method method) {
            return hasNoParameters(method) || (hasOnlyOneParameter(method) && inputTypeMatches(method));
        }

        private boolean hasNoParameters(Method method) {
            return method.getParameterTypes().length == 0;
        }

        private boolean annotationPresent(Method method) {
            return method.isAnnotationPresent(ReactOn.class);
        }

        private boolean hasOnlyOneParameter(Method method) {
            return method.getParameterTypes().length == 1;
        }

        private boolean inputTypeMatches(Method method) {
            return ReactorRequest.class.isAssignableFrom(method.getParameterTypes()[0]);
        }

        private boolean returnTypeMatches(Method method) {
            return ReactorResponse.class.isAssignableFrom(method.getReturnType());
        }
    };

    private List<Reactor> subReactors = newArrayList();

    public AbstractNestingReactor() {
        super(String.class);

        registerAnnotatedNestedReactors();
    }

    private void registerAnnotatedNestedReactors() {
        List<Method> reactorMethods = from(asList(getClass().getMethods())).filter(REACTOR_METHOD).toList();
        for (Method reactorMethod : reactorMethods) {
            Reactor methodProxyReactor = newMethodProxyReactor(reactorMethod);

            LOG.debug("Registering method proxy sub-reactor for method: {}.{}", getClass().getName(),
                reactorMethod.getName());
            registerNestedReactor(methodProxyReactor);
        }
    }

    private Reactor newMethodProxyReactor(Method annotatedNestingReactorMethod) {
        return new NestingReactorMethodProxyReactor(this, annotatedNestingReactorMethod);
    }

    public final ReactorResponse doReact(ReactorRequest<String> reactorRequest) {
        ReactorRequestInput requestInput = new ReactorRequestInput(reactorRequest.getRequestData());
        ReactorRequestInput subReactorInput = requestInput.subRequest();

        Optional<Reactor> subReactor = from(subReactors).filter(TRIGGER_MATCHES(subReactorInput)).first();
        if (subReactor.isPresent()) {
            return subReactor.get().react(reactorRequest.getSender(), subReactorInput.getArguments());
        }
        LOG.debug("Unable to find nested reactor for input data {}", subReactorInput);
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
