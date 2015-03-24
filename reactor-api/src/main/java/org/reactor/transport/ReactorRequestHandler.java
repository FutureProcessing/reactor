package org.reactor.transport;

import com.google.common.util.concurrent.ListenableFuture;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;

public interface ReactorRequestHandler {

    public ListenableFuture<?> handleReactorRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer);
}
