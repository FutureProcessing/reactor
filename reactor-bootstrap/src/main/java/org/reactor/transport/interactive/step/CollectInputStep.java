package org.reactor.transport.interactive.step;

import static java.lang.String.format;
import static org.reactor.request.ArgumentsParser.parseArguments;

import java.io.IOException;
import java.io.Writer;

import org.reactor.request.ReactorRequestInput;
import org.reactor.request.parser.ReactorRequestInputDefinition;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class CollectInputStep extends AbstractJourneyStep<ReactorRequestInput> {

    private final ReactorRequestInputDefinition inputDefinition;
    private final Writer responseWriter;

    public CollectInputStep(ReactorRequestInputDefinition inputDefinition, Writer responseWriter) {
        this.inputDefinition = inputDefinition;
        this.responseWriter = responseWriter;
    }

    @Override
    public void doBeforeStep() {
        respond(getInputDescription(inputDefinition));
    }

    private String getInputDescription(ReactorRequestInputDefinition inputDefinition) {
        return format("Please pass reactor input (of type %s):", inputDefinition.getType());
    }

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, ReactorRequestInput journeySubject) {
        journeySubject.pushArguments(parseArguments(stepInput));
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
