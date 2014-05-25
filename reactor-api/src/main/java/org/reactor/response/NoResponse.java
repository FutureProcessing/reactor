package org.reactor.response;

import java.io.Writer;

public class NoResponse implements ReactorResponse {

    public static final ReactorResponse NO_RESPONSE = new NoResponse();

    private NoResponse() {}

    @Override
    public void renderResponse(Writer responseWriter) throws Exception {}
}
