package org.reactor.transport;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;

import java.util.concurrent.Future;

public interface ReactorRequestHandler {

    public Future<?> handleReactorRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer);
}
