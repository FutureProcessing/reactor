package org.reactor.annotation;

import org.reactor.AbstractReactor;

public abstract class AbstractAnnotatedReactor extends AbstractReactor {

    private String reactorTrigger;

    private String reactorDescription;

    public AbstractAnnotatedReactor() {
        readAnnotatedReactor();
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

    @Override
    public final String getTriggeringExpression() {
        return reactorTrigger;
    }

    @Override
    public final String getDescription() {
        return reactorDescription;
    }
}
