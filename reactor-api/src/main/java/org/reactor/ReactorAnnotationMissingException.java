package org.reactor;

public class ReactorAnnotationMissingException extends ReactorInitializationException {

    public ReactorAnnotationMissingException(Reactor reactor) {
        super("ReactOn annotation missing for reactor: %s", reactor.getClass().getName());
    }
}
