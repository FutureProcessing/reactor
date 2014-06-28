package org.reactor.discovery;

import org.reactor.Reactor;

public interface ReactorTopologyDiscoveringVisitor {

    void visitReactorRequestParameter(String parameterName, boolean required);

    void visitSubReactor(Reactor subReactor);

}
