package org.reactor.transport;

public class ReactorMessageTransportException extends RuntimeException {

    public ReactorMessageTransportException(String cause) {
        super(cause);
    }

    public ReactorMessageTransportException(Throwable throwable) {
        super(throwable);
    }
}
