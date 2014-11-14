package org.reactor.discovery;

import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;

public class ReactorParametersDiscoveringVisitor implements ReactorTopologyDiscoveringVisitor {

    private List<ReactorRequestInputParameterDefinition> parameters;

    public ReactorParametersDiscoveringVisitor() {
        parameters = newArrayList();
    }

    @Override
    public void visitReactorRequestInputParameter(ReactorRequestInputParameterDefinition parameterDefinition) {
        parameters.add(parameterDefinition);
    }

    @Override
    public void visitReactorRequestInput(ReactorRequestInputDefinition inputDefinition) {
        // do nothing
    }

    @Override
    public void visitSubReactor(Reactor subReactor) {
        // do nothing
    }

    public List<ReactorRequestInputParameterDefinition> getParameters() {
        return parameters;
    }
}
