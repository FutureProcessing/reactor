package org.reactor.reactor;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.request.ReactorRequestInput.TRIGGER_MATCHES;
import static org.reactor.utils.ClassUtils.PossibleTypeAction;
import static org.reactor.utils.ClassUtils.tryCall;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;

import org.reactor.InitializingReactor;
import org.reactor.Reactor;
import org.reactor.ReactorProperties;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.reactor.request.ReactorRequestInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class ReactorController {

    private final static Logger LOG = LoggerFactory.getLogger(ReactorController.class);

    private final List<Reactor> reactors = newArrayList();

    public ReactorController() {
        collectReactors();
    }

    public Optional<Reactor> reactorMatchingInput(ReactorRequestInput requestInput) {
        return from(reactors).filter(TRIGGER_MATCHES(requestInput)).first();
    }

    private void collectReactors() {
        collectReactor(new ReactorControllerContentsReactor(this));

        ServiceLoader<Reactor> reactorsLoader = ServiceLoader.load(Reactor.class);

        for (Reactor reactor : reactorsLoader) {
            collectReactor(reactor);
        }
    }

    @VisibleForTesting
    void collectReactor(Reactor reactor) {
        LOG.debug("Reading reactor: {}", reactor.getClass().getName());
        reactors.add(reactor);
    }

    public void initReactors(ReactorProperties reactorProperties) {
        reactors.stream().forEach(reactor -> tryInitReactor(reactor, reactorProperties));
    }

    private void tryInitReactor(final Reactor reactor, final ReactorProperties reactorProperties) {
        tryCall(reactor, InitializingReactor.class, new PossibleTypeAction<InitializingReactor, Void>() {

            @Override
            public Void invokeAction(InitializingReactor subject) {
                LOG.debug("Initializing reactor: {}", reactor.getClass().getName());
                subject.initReactor(new ReactorProperties(reactorProperties));
                return null;
            }
        });
    }

    public void initEventConsumers(ReactorEventConsumerFactory factory) {
        reactors.stream().forEach(reactor -> createEventConsumers(reactor, factory));
    }

    private void createEventConsumers(Reactor reactor, final ReactorEventConsumerFactory factory) {
        tryCall(reactor, EventProducingReactor.class, new PossibleTypeAction<EventProducingReactor, Void>() {

            @Override
            public Void invokeAction(EventProducingReactor subject) {
                subject.initReactorEventConsumers(factory);
                return null;
            }
        });
    }

    List<Reactor> getReactors() {
        return reactors;
    }
}
