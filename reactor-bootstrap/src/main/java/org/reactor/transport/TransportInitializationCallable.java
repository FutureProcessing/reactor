package org.reactor.transport;

import static org.reactor.loader.DifferentClassLoaderOperation.doInDifferentContextClassLoader;

import java.util.concurrent.Callable;

public class TransportInitializationCallable implements Callable<ReactorMessageTransport> {

    private final ReactorMessageTransport transport;
    private final TransportProperties transportProperties;
    private final ReactorRequestHandler messageProcessor;

    public TransportInitializationCallable(ReactorMessageTransport transport, TransportProperties transportProperties,
                                           ReactorRequestHandler messageProcessor) {
        this.transport = transport;
        this.transportProperties = transportProperties;
        this.messageProcessor = messageProcessor;
    }

    @Override
    public ReactorMessageTransport call() throws Exception {
        try {
            return doInDifferentContextClassLoader(transport.getClass().getClassLoader(), () -> {
                transport.startTransport(transportProperties, messageProcessor);
                return transport;
            });

        } catch (ReactorMessageTransportInitializationException e) {
            throw new Exception(e);
        }
    }
}
