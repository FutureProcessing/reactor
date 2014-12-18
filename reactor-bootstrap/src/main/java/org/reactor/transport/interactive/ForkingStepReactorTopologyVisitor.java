package org.reactor.transport.interactive;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.forking.ForkingStepOutcome;

public class ForkingStepReactorTopologyVisitor extends AbstractJourneyReactorTopologyVisitor {

    private final ForkingStepOutcome<ReactorRequestInput> forkingStepOutcome;

    public ForkingStepReactorTopologyVisitor(ForkingStepOutcome<ReactorRequestInput> forkingStepOutcome,
                                             ReactorResponseRenderer responseRenderer) {
        super(responseRenderer);
        this.forkingStepOutcome = forkingStepOutcome;
    }

    @Override
    protected void newJourneyStep(AbstractJourneyStep<ReactorRequestInput> journeyStep) {
        forkingStepOutcome.journeyStep(journeyStep);
    }
}
