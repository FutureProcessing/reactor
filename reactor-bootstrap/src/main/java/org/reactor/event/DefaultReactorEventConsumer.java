package org.reactor.event;

import com.google.common.base.Function;

import org.reactor.response.ReactorResponse;
import org.reactor.transport.TransportController;

public class DefaultReactorEventConsumer<T extends ReactorEvent> implements ReactorEventConsumer<T> {

    private final TransportController transportController;
    private final Function<T, ReactorResponse> responseCreator;

    public DefaultReactorEventConsumer(TransportController transportController,
                                       Function<T, ReactorResponse> responseCreator) {
        this.transportController = transportController;
        this.responseCreator = responseCreator;
    }

    @Override
    public void consumeEvent(T event) {
        // default event consumer just broadcasts events response object through every transport
        transportController.broadcast(responseCreator.apply(event));
    }
}
