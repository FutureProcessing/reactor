package org.reactor.transport.interactive;

import java.io.Writer;

import org.reactor.Reactor;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;
import org.reactor.transport.interactive.step.ChooseSubreactorStep;
import org.reactor.transport.interactive.step.CollectInputStep;
import org.reactor.transport.interactive.step.CollectParameterStep;
import org.reactor.travelling.step.AbstractJourneyStep;

public abstract class AbstractJourneyReactorTopologyVisitor implements ReactorTopologyDiscoveringVisitor {

    private final Writer responseWriter;

    private ChooseSubreactorStep chooseSubreactorStep;

    public AbstractJourneyReactorTopologyVisitor(Writer responseWriter) {
        this.responseWriter = responseWriter;
    }

    @Override
    public final void visitReactorRequestInputParameter(ReactorRequestInputParameterDefinition parameterDefinition) {
        newJourneyStep(new CollectParameterStep(parameterDefinition, responseWriter));
    }

    @Override
    public final void visitReactorRequestInput(ReactorRequestInputDefinition inputDefinition) {
        newJourneyStep(new CollectInputStep(inputDefinition, responseWriter));
    }

    @Override
    public final void visitSubReactor(Reactor subReactor) {
        if (chooseSubreactorStep == null) {
            chooseSubreactorStep = new ChooseSubreactorStep(responseWriter);
            newJourneyStep(chooseSubreactorStep);
        }
        chooseSubreactorStep.addSubReactor(subReactor);
    }

    protected abstract void newJourneyStep(AbstractJourneyStep<ReactorRequestInput> journeyStep);
}
