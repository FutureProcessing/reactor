package org.reactor.annotation;

public class AnnotatedNestingReactorMethodProxyAnnotationMissingException extends AnnotatedNestingReactorMethodInvokerException {

    public static final String CAUSE_TEMPLATE = "Unable to find method to invoke on %s nesting reactor";

    public AnnotatedNestingReactorMethodProxyAnnotationMissingException(AbstractAnnotatedNestingReactor nestingReactor) {
        super(CAUSE_TEMPLATE, nestingReactor.getClass().getName());
    }
}
