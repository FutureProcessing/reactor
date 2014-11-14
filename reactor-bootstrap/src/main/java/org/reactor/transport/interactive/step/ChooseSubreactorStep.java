package org.reactor.transport.interactive.step;

import static com.google.common.base.Joiner.on;
import static java.lang.String.format;

import org.reactor.Reactor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.transport.interactive.ForkingStepReactorTopologyVisitor;
import org.reactor.travelling.step.forking.AbstractForkingJourneyStep;
import org.reactor.travelling.step.forking.ForkingStepOutcome;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class ChooseSubreactorStep extends AbstractForkingJourneyStep<ReactorRequestInput> {

    private final Writer responseWriter;
    private final List<String> subReactors = new LinkedList<>();

    public ChooseSubreactorStep(Writer responseWriter) {
        this.responseWriter = responseWriter;
    }

    public void addSubReactor(Reactor subReactor) {
        subReactors.add(subReactor.getTriggeringExpression());

        ForkingStepOutcome stepOutcome = onStepInput(subReactor.getTriggeringExpression());
        subReactor.accept(new ForkingStepReactorTopologyVisitor(stepOutcome, responseWriter));
    }

    @Override
    public void doBeforeStep() {
        respond(format("Please choose one of following: %s", on(", ").join(subReactors)));
    }

    @Override
    protected void doBeforeForking(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments(stepInput);
    }

    private void respond(String responseText) {
        try {
            responseWriter.write(responseText);
            responseWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
