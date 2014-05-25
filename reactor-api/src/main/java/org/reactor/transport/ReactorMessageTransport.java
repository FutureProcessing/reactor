package org.reactor.transport;

import org.reactor.response.ReactorResponse;

public interface ReactorMessageTransport {

    void startTransport(TransportProperties transportProperties, ReactorMessageTransportProcessor messageProcessor);

    void stopTransport();

    void broadcast(ReactorResponse reactorResponse);

    boolean isRunning();
}
