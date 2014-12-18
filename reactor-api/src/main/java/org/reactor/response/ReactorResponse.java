package org.reactor.response;

import org.reactor.response.renderer.ReactorResponseRenderer;

public interface ReactorResponse {

    void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception;
}