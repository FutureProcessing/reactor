package org.reactor.event;

import org.reactor.Reactor;

public interface EventProducingReactor extends Reactor {

    void initReactorEventConsumers(ReactorEventConsumerFactory factory);
}
