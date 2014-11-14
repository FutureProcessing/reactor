package org.reactor.transport.interactive;

import java.io.Writer;

import org.reactor.request.ReactorRequestInput;
import org.reactor.travelling.JourneyScenarioBuilder;
import org.reactor.travelling.step.AbstractJourneyStep;

public class JourneyScenarioReactorTopologyVisitor extends AbstractJourneyReactorTopologyVisitor {

    private final JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder;

    public JourneyScenarioReactorTopologyVisitor(JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder,
                                                 Writer responseWriter) {
        super(responseWriter);
        this.scenarioBuilder = scenarioBuilder;
    }

    @Override
    protected void newJourneyStep(AbstractJourneyStep<ReactorRequestInput> journeyStep) {
        scenarioBuilder.journeyStep(journeyStep);
    }
}
