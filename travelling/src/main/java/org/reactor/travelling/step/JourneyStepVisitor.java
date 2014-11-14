package org.reactor.travelling.step;

import org.reactor.travelling.step.forking.ForkingStepOutcome;

public interface JourneyStepVisitor<SUBJECT> {

    AbstractJourneyStep<SUBJECT> moveForward();

    AbstractJourneyStep<SUBJECT> moveBackward();

    void repeat();

    void fork(ForkingStepOutcome<SUBJECT> forkingStepOutcome);

    void endJourney();
}
