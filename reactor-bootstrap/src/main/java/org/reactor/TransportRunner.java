package org.reactor;

import static org.reactor.properties.PropertiesBuilder.propertiesBuilder;

import com.google.common.base.Supplier;

import org.reactor.event.DefaultReactorEventConsumerFactory;
import org.reactor.reactor.ReactorController;
import org.reactor.transport.DefaultTransportMessageProcessor;
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

    private void initReactorController(ReactorProperties reactorProperties) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        LOG.debug("Initializing Reactor Controller ...");
        reactorController = new ReactorController();
        reactorController.initReactors(reactorProperties);
        reactorController.initEventConsumers(new DefaultReactorEventConsumerFactory(transportController));
    }

    private void initTransportController(TransportProperties transportProperties) {
        transportController = TransportController.createAndLoadTransports();
        transportController.startTransports(transportProperties, new DefaultTransportMessageProcessor(
            new Supplier<ReactorController>() {

                @Override
                public ReactorController get() {
                    return reactorController;
                }
            }));
    }

    public final void start() throws Exception {
        initTransportController(new TransportProperties(propertiesBuilder()
            .loadFromResourceStream(TRANSPORT_PROPERTIES).build()));
        initReactorController(new ReactorProperties(propertiesBuilder().loadFromResourceStream(REACTOR_PROPERTIES)
            .build()));
    }

    public static void main(String[] args) {
        try {
            new TransportRunner().start();
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
}
