package org.reactor.annotation;

import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class AnnotatedNestingReactorMethodParametersMissingException extends AnnotatedNestingReactorMethodInvokerException {

    public AnnotatedNestingReactorMethodParametersMissingException(ReactorRequestParameterDefinition option) {
        super("Missing value for required option: %s", option.getName());
    }
}
