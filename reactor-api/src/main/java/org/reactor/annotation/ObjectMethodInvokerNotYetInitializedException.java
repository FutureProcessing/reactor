package org.reactor.annotation;

public class ObjectMethodInvokerNotYetInitializedException extends ObjectMethodInvokerInitializationException {

    public static final String MESSAGE_NOT_INITIALIZED = "Invoker is not yet initialized!";

    public ObjectMethodInvokerNotYetInitializedException() {
        super(MESSAGE_NOT_INITIALIZED);
    }
}
