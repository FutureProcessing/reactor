package org.reactor.discovery;

import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;

public interface ReactorTopologyDiscoveringVisitor {

    void visitReactorRequestInputParameter(ReactorRequestInputParameterDefinition parameterDefinition);

    void visitReactorRequestInput(ReactorRequestInputDefinition inputDefinition);

    void visitSubReactor(Reactor subReactor);
}
