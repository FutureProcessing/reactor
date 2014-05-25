package org.reactor.transport.alive;

import static org.reactor.transport.alive.HeartBeatAlivePolicy.ALIVE_ALWAYS;

import org.reactor.transport.TransportProperties;
import org.reactor.transport.ReactorMessageTransport;
import org.reactor.transport.ReactorMessageTransportProcessor;

public abstract class KeepAliveReactorMessageTransport implements ReactorMessageTransport {

    @Override
    public final void startTransport(TransportProperties transportProperties, ReactorMessageTransportProcessor messageProcessor) {
        startTransportKeptAlive(transportProperties, messageProcessor);

        new Thread(new HeartBeatThreadRunnable(ALIVE_ALWAYS)).start();
    }

    protected abstract void startTransportKeptAlive(TransportProperties transportProperties,
                                                    ReactorMessageTransportProcessor messageProcessor);

}
