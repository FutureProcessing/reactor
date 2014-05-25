package org.reactor.annotation;

import static java.lang.String.format;

public class AnnotatedNestingReactorMethodInvokerException extends RuntimeException {

    public AnnotatedNestingReactorMethodInvokerException(Throwable cause) {
        super(cause);
    }

    public AnnotatedNestingReactorMethodInvokerException(String causeTemplate, Object... parameters) {
        super(format(causeTemplate, parameters));
    }
}
