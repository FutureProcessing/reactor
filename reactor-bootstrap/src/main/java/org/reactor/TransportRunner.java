package org.reactor;

import static org.reactor.properties.PropertiesBuilder.propertiesBuilder;
import static org.reactor.utils.ClassUtils.newInstance;
import static org.reactor.utils.ClassUtils.tryCall;

import com.google.common.base.Supplier;

import org.reactor.event.DefaultReactorEventConsumerFactory;
import org.reactor.event.EventProducingReactor;
import org.reactor.transport.DefaultTransportMessageProcessor;
import org.reactor.transport.TransportController;
import org.reactor.transport.TransportProperties;
import org.reactor.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TransportRunner {

    private final static String REACTOR_PROPERTIES = "reactor.properties";
    private final static String TRANSPORT_PROPERTIES = "transport.properties";

    private final static Logger LOG = LoggerFactory.getLogger(TransportRunner.class);

    private TransportController transportController;
    private Reactor reactor;

    private void initReactor(ReactorProperties reactorProperties) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        LOG.debug("Trying to use reactor implementation: {}", reactorProperties.getReactorImplementation());
        reactor = newInstance(reactorProperties.getReactorImplementation(), Reactor.class);
        tryInitReactor(reactorProperties);
        tryInitReactorEvents();
    }

    private void tryInitReactor(final ReactorProperties reactorProperties) {
        tryCall(reactor, InitializingReactor.class, new ClassUtils.PossibleTypeAction<InitializingReactor, Void>() {

            @Override
            public Void invokeAction(InitializingReactor subject) {
                subject.initReactor(new ReactorProperties(reactorProperties));
                return null;
            }
        });
    }

    private void tryInitReactorEvents() {
        tryCall(reactor, EventProducingReactor.class, new ClassUtils.PossibleTypeAction<EventProducingReactor, Void>() {

            @Override
            public Void invokeAction(EventProducingReactor subject) {
                subject.initReactorEventConsumers(new DefaultReactorEventConsumerFactory(transportController));
                return null;
            }
        });
    }

    private void initTransportController(TransportProperties transportProperties) {
        transportController = new TransportController(new DefaultTransportMessageProcessor(new Supplier<Reactor>() {

            @Override
            public Reactor get() {
                return reactor;
            }
        }), transportProperties);
        transportController.startTransports();
    }

    public final void start() throws Exception {
        initTransportController(new TransportProperties(propertiesBuilder()
            .loadFromResourceStream(TRANSPORT_PROPERTIES).build()));
        initReactor(new ReactorProperties(propertiesBuilder().loadFromResourceStream(REACTOR_PROPERTIES).build()));
    }

    public static void main(String[] args) {
        try {
            new TransportRunner().start();
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
}
