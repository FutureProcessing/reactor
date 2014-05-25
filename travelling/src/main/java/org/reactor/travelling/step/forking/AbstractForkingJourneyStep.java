package org.reactor.travelling.step.forking;

import static com.google.common.collect.Maps.newHashMap;

import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;
import org.reactor.travelling.step.direction.ForkJourneyStepDirection;

import java.util.Map;

public abstract class AbstractForkingJourneyStep<SUBJECT> extends AbstractJourneyStep<SUBJECT> {

    private Map<String, ForkingStepOutcome<SUBJECT>> outcomes = newHashMap();

    protected ForkingStepOutcome onStepInput(String stepInput) {
        ForkingStepOutcome<SUBJECT> outcome = new ForkingStepOutcome<>();
        outcomes.put(stepInput, outcome);
        return outcome;
    }

    protected final AbstractJourneyStepDirection<SUBJECT> doJourneyStep(String stepInput, SUBJECT journeySubject) {
        if (!outcomes.containsKey(stepInput)) {
            return doForkingJourneyStep(stepInput, journeySubject);
        }
        return fork(outcomes.get(stepInput));
    }

    protected abstract AbstractJourneyStepDirection<SUBJECT> doForkingJourneyStep(String stepInput,
                                                                                  SUBJECT journeySubject);

    protected final AbstractJourneyStepDirection<SUBJECT> fork(ForkingStepOutcome<SUBJECT> forkingStepOutcome) {
        return new ForkJourneyStepDirection<>(forkingStepOutcome);
    }

}
