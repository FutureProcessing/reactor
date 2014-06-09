package org.reactor.transport.directinput;

import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;

public class DirectInputMessageTransport extends KeepAliveReactorMessageTransport {

    private boolean running;
    private DirectInputProcessor inputProcessor;

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties,
                                           ReactorMessageTransportProcessor messageProcessor) {
        inputProcessor = new DirectInputProcessor(messageProcessor);
        running = true;
    }

    @Override
    public void stopTransport() {
        inputProcessor.stopProcessing();
        running = false;
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        // do nothing
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}
