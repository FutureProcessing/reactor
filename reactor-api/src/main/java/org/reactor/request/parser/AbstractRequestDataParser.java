package org.reactor.request.parser;

import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;

public abstract class AbstractRequestDataParser<T> {

    public abstract ReactorRequest<T> parseRequestWithData(String sender, String trigger, String reactorInput);

    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {}
}
