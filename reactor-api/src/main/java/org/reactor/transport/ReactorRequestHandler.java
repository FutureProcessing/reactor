package org.reactor.transport;

import org.reactor.request.ReactorRequestInput;

import java.io.Writer;

public interface ReactorRequestHandler {

    public void handleReactorRequest(ReactorRequestInput requestInput, String sender, Writer responseWriter);
}
