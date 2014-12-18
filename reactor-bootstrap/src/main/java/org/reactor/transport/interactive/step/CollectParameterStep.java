package org.reactor.transport.interactive.step;

import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class CollectParameterStep extends AbstractJourneyStep<ReactorRequestInput> {

    private final ReactorRequestInputParameterDefinition parameterDefinition;
    private final ReactorResponseRenderer responseRenderer;

    public CollectParameterStep(ReactorRequestInputParameterDefinition parameterDefinition,
                                ReactorResponseRenderer responseRenderer) {
        this.parameterDefinition = parameterDefinition;
        this.responseRenderer = responseRenderer;
    }

    @Override
    public void doBeforeStep() {
        responseRenderer.renderTextLine("Please pass parameter %s:", parameterDefinition.getName());
    }

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments("--" + parameterDefinition.getName(), stepInput);
        return forward();
    }
}
