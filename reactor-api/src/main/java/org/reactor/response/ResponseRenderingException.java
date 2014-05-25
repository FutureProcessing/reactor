package org.reactor.response;

public class ResponseRenderingException extends Exception {

    public ResponseRenderingException(String reason) {
        super(reason);
    }

    public ResponseRenderingException(Throwable reason) {
        super(reason);
    }
}
