package org.reactor.annotation;

import org.reactor.Reactor;
import org.reactor.ReactorInitializationException;

public class ReactorAnnotationMissingException extends ReactorInitializationException {

    public ReactorAnnotationMissingException(Reactor reactor) {
        super("ReactOn annotation missing for reactor: %s", reactor.getClass().getName());
    }
}
