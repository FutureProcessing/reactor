package org.reactor.reactor;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.compile;
import static org.reactor.utils.ClassUtils.PossibleTypeAction;
import static org.reactor.utils.ClassUtils.tryCall;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.ServiceLoader;
import org.reactor.InitializingReactor;
import org.reactor.Reactor;
import org.reactor.ReactorProperties;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorController {

    private final static Predicate<Reactor> ACCEPTING_INPUT(final String reactorInput) {
        return new Predicate<Reactor>() {

            @Override
            public boolean apply(Reactor input) {
                return compile(input.getTriggeringExpression() + " .*").matcher(reactorInput).find();
            }
        };
    }

    private final static Logger LOG = LoggerFactory.getLogger(ReactorController.class);

    private final List<Reactor> reactors = newArrayList();

    public ReactorController() {
        collectReactors();
    }

    public Optional<Reactor> reactorMatchingInput(String requestInput) {
        return from(reactors).filter(ACCEPTING_INPUT(requestInput)).first();
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
        for (Reactor reactor : reactors) {
            tryInitReactor(reactor, reactorProperties);
        }
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
        for (Reactor reactor : reactors) {
            createEventConsumers(reactor, factory);
        }
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
