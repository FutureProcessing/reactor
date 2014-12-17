package org.reactor.response;

import org.reactor.response.renderer.ReactorResponseRenderer;

public class NoResponse implements ReactorResponse {

    public static final ReactorResponse NO_RESPONSE = new NoResponse();

    private NoResponse() {}

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception {}
}
