package org.reactor.discovery;

import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public interface ReactorTopologyDiscoveringVisitor {

    void visitReactorRequestParameter(ReactorRequestParameterDefinition parameterDefinition);

    void visitSubReactor(Reactor subReactor);

}
