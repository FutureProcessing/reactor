package org.reactor.transport.interactive;

import org.reactor.Reactor;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.interactive.step.ChooseSubreactorStep;
import org.reactor.transport.interactive.step.CollectInputStep;
import org.reactor.transport.interactive.step.CollectParameterStep;
import org.reactor.travelling.step.AbstractJourneyStep;

public abstract class AbstractJourneyReactorTopologyVisitor implements ReactorTopologyDiscoveringVisitor {

    private final ReactorResponseRenderer responseRenderer;

    private ChooseSubreactorStep chooseSubreactorStep;

    public AbstractJourneyReactorTopologyVisitor(ReactorResponseRenderer responseRenderer) {
        this.responseRenderer = responseRenderer;
    }

    @Override
    public final void visitReactorRequestInputParameter(ReactorRequestInputParameterDefinition parameterDefinition) {
        newJourneyStep(new CollectParameterStep(parameterDefinition, responseRenderer));
    }

    @Override
    public final void visitReactorRequestInput(ReactorRequestInputDefinition inputDefinition) {
        newJourneyStep(new CollectInputStep(inputDefinition, responseRenderer));
    }

    @Override
    public final void visitSubReactor(Reactor subReactor) {
        if (chooseSubreactorStep == null) {
            chooseSubreactorStep = new ChooseSubreactorStep(responseRenderer);
            newJourneyStep(chooseSubreactorStep);
        }
        chooseSubreactorStep.addSubReactor(subReactor);
    }

    protected abstract void newJourneyStep(AbstractJourneyStep<ReactorRequestInput> journeyStep);
}
