package org.reactor.transport;

public class ReactorMessageTransportInitializationException extends ReactorMessageTransportException {

    public ReactorMessageTransportInitializationException(String cause) {
        super(cause);
    }

    public ReactorMessageTransportInitializationException(Throwable throwable) {
        super(throwable);
    }
}
