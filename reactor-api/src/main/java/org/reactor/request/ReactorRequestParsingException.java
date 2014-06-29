package org.reactor.request;

public class ReactorRequestParsingException extends RuntimeException {

    public ReactorRequestParsingException(Throwable cause) {
        super(cause);
    }

    public ReactorRequestParsingException(String cause) {
        super(cause);
    }
}
