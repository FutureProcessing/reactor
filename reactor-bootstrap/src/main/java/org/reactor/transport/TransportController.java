package org.reactor.transport;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.util.concurrent.Futures.addCallback;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static java.util.concurrent.Executors.newFixedThreadPool;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.List;
import java.util.ServiceLoader;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportController {

    private final static Logger LOG = LoggerFactory.getLogger(TransportController.class);

    private final static ListeningExecutorService executorService = listeningDecorator(newFixedThreadPool(10));

    private final ReactorMessageTransportProcessor transportProcessor;
    private final TransportProperties transportProperties;
    private final List<ReactorMessageTransport> transports = newArrayList();

    public TransportController(ReactorMessageTransportProcessor transportProcessor, TransportProperties transportProperties) {
        this.transportProcessor = transportProcessor;
        this.transportProperties = transportProperties;

        loadTransports();
    }

    private void loadTransports() {
        ServiceLoader<ReactorMessageTransport> transportsLoader = ServiceLoader.load(ReactorMessageTransport.class);
        for (ReactorMessageTransport transport : transportsLoader) {
            LOG.debug("Loading message transport: {}", transport.getClass().getName());
            transports.add(transport);
        }
    }

    public final void startTransports() {
        for (ReactorMessageTransport transport : transports) {
            startTransport(transport);
        }
        executorService.shutdown();
        new TransportsShutdownHook(this).initHook();
    }

    private void startTransport(ReactorMessageTransport transport) {
        addCallback(executorService.submit(new TransportInitializationCallable(transport, transportProperties,
            transportProcessor)), new TransportInitializationCallback(transport));
    }

    public final void stopTransports() {
        for (ReactorMessageTransport transport : transports) {
            LOG.debug("Shutting down transport: {}", transport.getClass().getName());
            transport.stopTransport();
            LOG.debug("Transport stopped: {}", transport.getClass().getName());
        }
    }

    public void broadcast(ReactorResponse reactorResponse) {
        for (ReactorMessageTransport transport : transports) {
            if (!validateTransportForBroadcast(transport)) {
                continue;
            }
            transport.broadcast(reactorResponse);
        }
    }

    private boolean validateTransportForBroadcast(ReactorMessageTransport transport) {
        if (!transport.isRunning()) {
            LOG.trace("Transport is not started up yet: {}", transport.getClass().getName());
            return false;
        }
        return true;
    }
}
