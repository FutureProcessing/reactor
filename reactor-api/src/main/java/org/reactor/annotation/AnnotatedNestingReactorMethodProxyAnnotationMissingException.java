package org.reactor.annotation;

import org.reactor.AbstractNestingReactor;

public class AnnotatedNestingReactorMethodProxyAnnotationMissingException extends AnnotatedNestingReactorMethodInvokerException {

    public static final String CAUSE_TEMPLATE = "Unable to find method to invoke on %s nesting reactor";

    public AnnotatedNestingReactorMethodProxyAnnotationMissingException(AbstractNestingReactor nestingReactor) {
        super(CAUSE_TEMPLATE, nestingReactor.getClass().getName());
    }
}
