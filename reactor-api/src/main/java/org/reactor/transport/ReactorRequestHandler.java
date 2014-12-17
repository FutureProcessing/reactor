package org.reactor.transport;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;

public interface ReactorRequestHandler {

    public void handleReactorRequest(ReactorRequestInput requestInput, String sender,
                                     ReactorResponseRenderer responseRenderer);
}
