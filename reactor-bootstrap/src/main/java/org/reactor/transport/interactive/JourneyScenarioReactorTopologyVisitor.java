package org.reactor.transport.interactive;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.travelling.JourneyScenarioBuilder;
import org.reactor.travelling.step.AbstractJourneyStep;

public class JourneyScenarioReactorTopologyVisitor extends AbstractJourneyReactorTopologyVisitor {

    private final JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder;

    public JourneyScenarioReactorTopologyVisitor(JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder,
                                                 ReactorResponseRenderer responseRenderer) {
        super(responseRenderer);
        this.scenarioBuilder = scenarioBuilder;
    }

    @Override
    protected void newJourneyStep(AbstractJourneyStep<ReactorRequestInput> journeyStep) {
        scenarioBuilder.journeyStep(journeyStep);
    }
}
