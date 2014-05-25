package org.reactor.annotation;

import static java.lang.String.format;

public class ObjectMethodInvokerInitializationException extends RuntimeException {

    public ObjectMethodInvokerInitializationException(String causeTemplate, String... causeParameters) {
        super(format(causeTemplate, causeParameters));
    }
}
