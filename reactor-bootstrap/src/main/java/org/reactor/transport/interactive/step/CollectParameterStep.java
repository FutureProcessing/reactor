package org.reactor.transport.interactive.step;

import static java.lang.String.format;

import java.io.IOException;
import java.io.Writer;

import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputParameterDefinition;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class CollectParameterStep extends AbstractJourneyStep<ReactorRequestInput> {

    private final ReactorRequestInputParameterDefinition parameterDefinition;
    private final Writer responseWriter;

    public CollectParameterStep(ReactorRequestInputParameterDefinition parameterDefinition, Writer responseWriter) {
        this.parameterDefinition = parameterDefinition;
        this.responseWriter = responseWriter;
    }

    @Override
    public void doBeforeStep() {
        respond(getParameterDescription(parameterDefinition));
    }

    private String getParameterDescription(ReactorRequestInputParameterDefinition parameterDefinition) {
        return format("Please pass parameter %s:", parameterDefinition.getName());
    }

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments("--" + parameterDefinition.getName(), stepInput);
        return forward();
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
