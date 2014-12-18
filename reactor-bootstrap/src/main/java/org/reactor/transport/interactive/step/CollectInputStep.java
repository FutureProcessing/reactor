package org.reactor.transport.interactive.step;

import static org.reactor.request.ArgumentsParser.parseArguments;

import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class CollectInputStep extends AbstractJourneyStep<ReactorRequestInput> {

    private final ReactorRequestInputDefinition inputDefinition;
    private final ReactorResponseRenderer responseRenderer;

    public CollectInputStep(ReactorRequestInputDefinition inputDefinition, ReactorResponseRenderer responseRenderer) {
        this.inputDefinition = inputDefinition;
        this.responseRenderer = responseRenderer;
    }

    @Override
    public void doBeforeStep() {
        responseRenderer.renderTextLine("Please pass reactor input (of type %s):", inputDefinition.getType());
    }

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments(parseArguments(stepInput));
        return forward();
    }
}