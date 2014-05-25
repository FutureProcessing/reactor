package org.reactor;

import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

public interface Reactor {

    String getTriggeringExpression();

    String getDescription();

    ReactorResponse react(ReactorRequest reactorRequest);
}
