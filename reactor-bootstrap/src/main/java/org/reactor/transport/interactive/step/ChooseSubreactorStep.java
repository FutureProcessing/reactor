package org.reactor.transport.interactive.step;

import static com.google.common.base.Joiner.on;

import org.reactor.Reactor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.interactive.ForkingStepReactorTopologyVisitor;
import org.reactor.travelling.step.forking.AbstractForkingJourneyStep;
import org.reactor.travelling.step.forking.ForkingStepOutcome;

import java.util.LinkedList;
import java.util.List;

public class ChooseSubreactorStep extends AbstractForkingJourneyStep<ReactorRequestInput> {

    private final ReactorResponseRenderer responseRenderer;
    private final List<String> subReactors = new LinkedList<>();

    public ChooseSubreactorStep(ReactorResponseRenderer responseRenderer) {
        this.responseRenderer = responseRenderer;
    }

    public void addSubReactor(Reactor subReactor) {
        subReactors.add(subReactor.getTriggeringExpression());

        ForkingStepOutcome<ReactorRequestInput> stepOutcome = onStepInput(subReactor.getTriggeringExpression());
        subReactor.accept(new ForkingStepReactorTopologyVisitor(stepOutcome, responseRenderer));
    }

    @Override
    public void doBeforeStep() {
        responseRenderer.renderTextLine("Please choose one of following: %s", on(", ").join(subReactors));
    }

    @Override
    protected void doBeforeForking(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments(stepInput);
    }
}
