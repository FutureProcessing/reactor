package org.reactor.event;

public interface ReactorEventConsumer<T extends ReactorEvent> {

    void consumeEvent(T event);
}
