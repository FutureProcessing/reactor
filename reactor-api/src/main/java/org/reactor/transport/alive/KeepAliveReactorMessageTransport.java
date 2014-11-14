package org.reactor.transport.alive;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.reactor.transport.alive.HeartBeatAlivePolicy.ALIVE_ALWAYS;

import org.reactor.transport.TransportProperties;
import org.reactor.transport.ReactorMessageTransport;
import org.reactor.transport.ReactorRequestHandler;

import java.util.concurrent.ExecutorService;

public abstract class KeepAliveReactorMessageTransport implements ReactorMessageTransport {

    private ExecutorService executorService;

    @Override
    public final void startTransport(TransportProperties transportProperties, ReactorRequestHandler messageProcessor) {
        startTransportKeptAlive(transportProperties, messageProcessor);

        executorService = newSingleThreadExecutor();
        executorService.submit(new HeartBeatThreadRunnable(ALIVE_ALWAYS));
    }

    @Override
    public final void stopTransport() {
        executorService.shutdown();

        stopTransportKeptAlive();
    }

    protected abstract void stopTransportKeptAlive();

    protected abstract void startTransportKeptAlive(TransportProperties transportProperties,
                                                    ReactorRequestHandler requestHandler);

}
