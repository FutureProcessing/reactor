package org.reactor.annotation;

import java.lang.reflect.Method;
import org.reactor.AbstractNestingReactor;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAnnotatedNestingReactor extends AbstractNestingReactor {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractAnnotatedNestingReactor.class);

    private String reactorTrigger;
    private String reactorDescription;

    public AbstractAnnotatedNestingReactor() {
        readAnnotatedReactor();
        readAnnotatedNestedReactors();
    }

    private void readAnnotatedReactor() {
        Class<?> reactorClass = this.getClass();
        if (!reactorClass.isAnnotationPresent(ReactOn.class)) {
            throw new ReactorAnnotationMissingException(this);
        }
        ReactOn reactOn = reactorClass.getAnnotation(ReactOn.class);
        reactorTrigger = reactOn.value();
        reactorDescription = reactOn.description();
    }

    private void readAnnotatedNestedReactors() {
        Method[] reactorMethods = this.getClass().getMethods();
        for (Method reactorMethod : reactorMethods) {
            if (!reactorMethod.isAnnotationPresent(ReactOn.class)) {
                continue;
            }
            readReactorMethod(reactorMethod);
        }
    }

    private void readReactorMethod(Method annotatedNestingReactorMethod) {
        Class<?> returnType = annotatedNestingReactorMethod.getReturnType();
        if (!ReactorResponse.class.isAssignableFrom(returnType)) {
            LOG.warn("Wrong method return type: {}, should be an instance of {} class.", returnType.getName(),
                ReactorResponse.class.getName());
            return;
        }
        ReactOn reactorAnnotation = annotatedNestingReactorMethod.getAnnotation(ReactOn.class);
        AnnotatedNestingReactorMethodProxyReactor nestedReactor = new AnnotatedNestingReactorMethodProxyReactor(this,
            annotatedNestingReactorMethod.getName(), reactorAnnotation.value(), reactorAnnotation.description());
        registerNestedReactor(nestedReactor);
    }

    @Override
    public final String getTriggeringExpression() {
        return reactorTrigger;
    }

    @Override
    public final String getDescription() {
        return reactorDescription;
    }
}
