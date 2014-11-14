package org.reactor.transport;


import java.util.concurrent.Callable;

public class TransportInitializationCallable implements Callable<ReactorMessageTransport> {

    private final ReactorMessageTransport transport;
    private final TransportProperties transportProperties;
    private final ReactorRequestHandler messageProcessor;

    public TransportInitializationCallable(ReactorMessageTransport transport,
                                           TransportProperties transportProperties,
                                           ReactorRequestHandler messageProcessor) {
        this.transport = transport;
        this.transportProperties = transportProperties;
        this.messageProcessor = messageProcessor;
    }

    @Override
    public ReactorMessageTransport call() throws Exception {
        try {
            transport.startTransport(transportProperties, messageProcessor);
        } catch (ReactorMessageTransportInitializationException e) {
            throw new Exception(e);
        }
        return transport;
    }
}
