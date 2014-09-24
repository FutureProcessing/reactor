package org.reactor.discovery;

import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class ReactorParametersDiscoveringVisitor implements ReactorTopologyDiscoveringVisitor {

    private List<ReactorRequestParameterDefinition> parameters;

    public ReactorParametersDiscoveringVisitor() {
        parameters = newArrayList();
    }

    @Override
    public void visitReactorRequestParameter(ReactorRequestParameterDefinition parameterDefinition) {
        parameters.add(parameterDefinition);
    }

    @Override
    public void visitSubReactor(Reactor subReactor) {
        // do nothing
    }

    public List<ReactorRequestParameterDefinition> getParameters() {
        return parameters;
    }
}
