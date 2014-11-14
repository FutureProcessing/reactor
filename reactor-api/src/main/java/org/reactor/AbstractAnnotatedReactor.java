package org.reactor;

import org.reactor.annotation.ReactOn;

public abstract class AbstractAnnotatedReactor<T> extends AbstractReactor<T> {

    private String reactorTrigger;
    private String reactorDescription;

    public AbstractAnnotatedReactor(Class<T> requestDataType) {
        super(requestDataType);
        readReactorAnnotations();
    }

    private void readReactorAnnotations() {
        Class<?> reactorClass = this.getClass();
        if (!reactorClass.isAnnotationPresent(ReactOn.class)) {
            throw new ReactorAnnotationMissingException(this);
        }
        ReactOn reactOn = reactorClass.getAnnotation(ReactOn.class);
        reactorTrigger = reactOn.value();
        reactorDescription = reactOn.description();
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
