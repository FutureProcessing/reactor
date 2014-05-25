package org.reactor.event;

import com.google.common.base.Function;

import org.reactor.response.ReactorResponse;
import org.reactor.transport.TransportController;

public class DefaultReactorEventConsumerFactory implements ReactorEventConsumerFactory {

    private final TransportController transportController;

    public DefaultReactorEventConsumerFactory(TransportController transportController) {
        this.transportController = transportController;
    }

    @Override
    public <T extends ReactorEvent> ReactorEventConsumer<T> createEventConsumer(Class<T> eventType,
                                                                                Function<T, ReactorResponse> responseCreator) {
        return new DefaultReactorEventConsumer<>(transportController, responseCreator);
    }
}
