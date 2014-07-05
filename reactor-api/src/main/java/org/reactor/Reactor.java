package org.reactor;

import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;

public interface Reactor {

    String getTriggeringExpression();

    String getDescription();

    ReactorResponse react(String sender, ReactorRequestInput requestInput);

    void accept(ReactorTopologyDiscoveringVisitor topologyVisitor);
}
