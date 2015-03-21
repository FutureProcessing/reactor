package org.reactor.response;

public class NoResponse implements ReactorResponse {

    public static final ReactorResponse NO_RESPONSE = new NoResponse();

    private NoResponse() {
    }
}
