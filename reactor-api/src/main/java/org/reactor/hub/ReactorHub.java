package org.reactor.hub;

import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import static org.reactor.utils.ClassUtils.PossibleTypeAction;
import static org.reactor.utils.ClassUtils.tryCall;
import org.reactor.AbstractReactor;
import org.reactor.InitializingReactor;
import org.reactor.Reactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.AbstractAnnotatedNestingReactor;
import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ServiceLoader;

public class ReactorHub extends AbstractReactor implements InitializingReactor, EventProducingReactor {

    private final static Logger LOG = LoggerFactory.getLogger(ReactorHub.class);

    private final List<Reactor> reactors = newArrayList();

    public ReactorHub() {
        collectReactors();
    }

    private void collectReactors() {
        reactors.add(new HubDetailsReactor());

        ServiceLoader<Reactor> reactorsLoader = ServiceLoader.load(Reactor.class);
        for (Reactor reactor : reactorsLoader) {
            LOG.debug("Reading reactor: {}", reactor.getClass().getName());
            reactors.add(reactor);
        }
    }

    @Override
    public void initReactorEventConsumers(ReactorEventConsumerFactory factory) {
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

    @Override
    public final void initReactor(ReactorProperties properties) {
        for (Reactor reactor : reactors) {
            tryInitReactor(reactor, properties);
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

    @Override
    protected final ReactorResponse doReact(ReactorRequest request) {
        for (Reactor reactor : reactors) {
            if (request.triggerMatches(reactor.getTriggeringExpression())) {
                LOG.debug("Triggering reactor: {}", reactor.getClass().getName());
                return reactor.react(request);
            }
        }
        LOG.debug("Unable to recognize reactor for request trigger: {}", request.getTrigger());
        return NO_RESPONSE;
    }

    @Override
    public String getTriggeringExpression() {
        // matches always
        return ".*";
    }

    @Override
    public String getDescription() {
        return "Reactor HUB";
    }

    @ReactOn(value = "!hub",
             description = "Reactor that gather a set of different reactors and merges them into one ;)")
    private class HubDetailsReactor extends AbstractAnnotatedNestingReactor implements InitializingReactor {

        @Override
        public void initReactor(ReactorProperties reactorProperties) {
            registerNestedReactor(new ListHubReactorsReactor());
        }
    }

    @ReactOn("list")
    private class ListHubReactorsReactor extends AbstractAnnotatedReactor {

        @Override
        public ReactorResponse doReact(ReactorRequest request) {
            return new ListReactorResponse<Reactor>("List of all registered reactors") {

                @Override
                protected Iterable<Reactor> getElements() {
                    return reactors;
                }

                @Override
                protected ListElementFormatter<Reactor> getElementFormatter() {
                    return new ListElementFormatter<Reactor>() {

                        @Override
                        public String formatListElement(long elementIndex, Reactor listElement) {
                            return String.format("%d. %s - %s", elementIndex, listElement.getTriggeringExpression(),
                                listElement.getDescription());
                        }
                    };
                }
            };
        }
    }

}
