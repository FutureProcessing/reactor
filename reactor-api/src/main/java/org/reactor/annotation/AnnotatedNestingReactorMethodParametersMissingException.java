package org.reactor.annotation;

public class AnnotatedNestingReactorMethodParametersMissingException extends AnnotatedNestingReactorMethodInvokerException {

    public AnnotatedNestingReactorMethodParametersMissingException(AnnotatedNestingReactorMethodProxyOption option) {
        super("Missing value for required option: %s", option.getName());
    }
}
