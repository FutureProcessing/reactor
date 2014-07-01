package org.reactor.transport;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.util.concurrent.Futures.addCallback;
import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static java.util.concurrent.Executors.newFixedThreadPool;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ListeningExecutorService;

import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class TransportController {

    private final static Logger LOG = LoggerFactory.getLogger(TransportController.class);

    private final static ListeningExecutorService executorService = listeningDecorator(newFixedThreadPool(10));

    private final List<ReactorMessageTransport> transports = newArrayList();

    public static TransportController createAndLoadTransports() {
        TransportController transportController = new TransportController();
        transportController.loadTransports();
        return transportController;
    }

    private TransportController() {}

    private void loadTransports() {
        ServiceLoader<ReactorMessageTransport> transportsLoader = ServiceLoader.load(ReactorMessageTransport.class);
        if (!transportsLoader.iterator().hasNext()) {
            LOG.warn("No message transports found!");
            return;
        }
        for (ReactorMessageTransport transport : transportsLoader) {
            LOG.debug("Loading message transport: {}", transport.getClass().getName());
            transports.add(transport);
        }
    }

    public final void startTransports(TransportProperties transportProperties,
                                      ReactorMessageTransportProcessor messageTransportProcessor) {

        transports.forEach(transport ->startTransport(transport, transportProperties, messageTransportProcessor));
        executorService.shutdown();

        new TransportsShutdownHook(this).initHook();
    }

    private void startTransport(ReactorMessageTransport transport, TransportProperties transportProperties,
                                ReactorMessageTransportProcessor messageTransportProcessor) {
        addCallback(executorService.submit(new TransportInitializationCallable(transport, transportProperties,
            messageTransportProcessor)), new TransportInitializationCallback(transport));
    }

    public final void stopTransports() {
        transports.forEach(transport -> {
            LOG.debug("Shutting down transport: {}", transport.getClass().getName());
            transport.stopTransport();
            LOG.debug("Transport stopped: {}", transport.getClass().getName());
        });
    }

    public void broadcast(ReactorResponse reactorResponse) {
        transports.stream()
                .filter(transport -> transport.isRunning())
                .forEach(transport -> transport.broadcast(reactorResponse));
    }

    @VisibleForTesting
    void addTransport(ReactorMessageTransport transport) {
        transports.add(transport);
    }
}
