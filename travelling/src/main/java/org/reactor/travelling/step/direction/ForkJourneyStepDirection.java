package org.reactor.travelling.step.direction;


import org.reactor.travelling.step.AbstractJourneyStepDirection;
import org.reactor.travelling.step.JourneyStepVisitor;
import org.reactor.travelling.step.forking.ForkingStepOutcome;

public class ForkJourneyStepDirection<SUBJECT> extends AbstractJourneyStepDirection<SUBJECT> {

    private final ForkingStepOutcome<SUBJECT> forkingStepOutcome;

    public ForkJourneyStepDirection(ForkingStepOutcome<SUBJECT> forkingStepOutcome) {
        this.forkingStepOutcome = forkingStepOutcome;
    }

    @Override
    public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
        journeyStepVisitor.fork(forkingStepOutcome);
    }
}
