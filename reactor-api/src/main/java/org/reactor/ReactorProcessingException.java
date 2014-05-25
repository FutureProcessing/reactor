package org.reactor;

public class ReactorProcessingException extends RuntimeException {

    public ReactorProcessingException(Throwable throwable) {
        super(throwable);
    }

    public ReactorProcessingException(String cause) {
        super(cause);
    }
}
