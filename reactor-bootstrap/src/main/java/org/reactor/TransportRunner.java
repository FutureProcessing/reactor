package org.reactor;

import static org.reactor.properties.PropertiesLoader.propertiesLoader;
import org.reactor.event.DefaultReactorEventConsumerFactory;
import org.reactor.reactor.ReactorController;
import org.reactor.transport.DefaultReactorRequestHandler;
import org.reactor.transport.TransportController;
import org.reactor.transport.TransportProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TransportRunner {

    private final static String REACTOR_PROPERTIES = "reactor.properties";
    private final static String TRANSPORT_PROPERTIES = "transport.properties";

    private final static Logger LOG = LoggerFactory.getLogger(TransportRunner.class);

    private TransportController transportController;
    private ReactorController reactorController;

    public final void start() {
        initReactorController(new ReactorProperties(propertiesLoader().fromResourceStream(REACTOR_PROPERTIES).load()));
        initTransportController(new TransportProperties(propertiesLoader().fromResourceStream(TRANSPORT_PROPERTIES).load()));

        initReactorControllerEventConsumers();
    }

    private void initTransportController(TransportProperties transportProperties) {
        LOG.debug("Initializing Transport Controller ...");
        transportController = TransportController.createAndLoadTransports();
        transportController.startTransports(transportProperties, new DefaultReactorRequestHandler(reactorController));
    }

    private void initReactorController(ReactorProperties reactorProperties) {
        LOG.debug("Initializing Reactor Controller ...");
        reactorController = new ReactorController();
        reactorController.initReactors(reactorProperties);
    }

    private void initReactorControllerEventConsumers() {
        reactorController.initEventConsumers(new DefaultReactorEventConsumerFactory(transportController));
    }

    public static void main(String[] args) {
        new TransportRunner().start();
    }
}
