package org.reactor.travelling.step.forking;

import static com.google.common.collect.Lists.newLinkedList;
import static org.reactor.travelling.JourneyScenarioBuilder.forSubject;

import org.reactor.travelling.JourneyScenario;
import org.reactor.travelling.step.AbstractJourneyStep;

import java.util.List;

public class ForkingStepOutcome<SUBJECT> {

    private final List<AbstractJourneyStep<SUBJECT>> stepList = newLinkedList();

    public ForkingStepOutcome<SUBJECT> journeyStep(AbstractJourneyStep<SUBJECT> forkedJourneyStep) {
        stepList.add(forkedJourneyStep);
        return this;
    }

    public JourneyScenario<SUBJECT> createForkedScenario(SUBJECT scenarioSubject,
                                                         JourneyScenario<SUBJECT> parentScenario) {
        return forSubject(scenarioSubject).parentScenario(parentScenario).journeySteps(stepList).build();
    }
}
