package org.reactor;

import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.response.ReactorResponse;

public interface Reactor {

    String getTriggeringExpression();

    String getDescription();

    ReactorResponse react(String sender, String reactorInput);

    void accept(ReactorTopologyDiscoveringVisitor topologyVisitor);
}
