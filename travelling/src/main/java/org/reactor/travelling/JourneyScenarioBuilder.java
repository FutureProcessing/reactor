package org.reactor.travelling;

import static com.google.common.collect.Lists.newLinkedList;
import java.util.List;
import org.reactor.travelling.step.AbstractJourneyStep;

public class JourneyScenarioBuilder<SUBJECT> {

    private final List<AbstractJourneyStep> stepList = newLinkedList();
    private SUBJECT scenarioSubject;
    private JourneyScenario<SUBJECT> parentScenario;

    private JourneyScenarioBuilder(SUBJECT scenarioSubject) {
        this.scenarioSubject = scenarioSubject;
    }

    public static <SUBJECT> JourneyScenarioBuilder<SUBJECT> forSubject(SUBJECT scenarioSubject) {
        return new JourneyScenarioBuilder<>(scenarioSubject);
    }

    public JourneyScenarioBuilder<SUBJECT> journeyStep(AbstractJourneyStep<SUBJECT> journeyStep) {
        stepList.add(journeyStep);
        return this;
    }

    public JourneyScenarioBuilder<SUBJECT> journeySteps(List<AbstractJourneyStep<SUBJECT>> journeyStep) {
        stepList.addAll(journeyStep);
        return this;
    }

    public JourneyScenario<SUBJECT> build() {
        return new JourneyScenario(parentScenario, scenarioSubject, stepList);
    }

    public JourneyScenarioBuilder<SUBJECT> parentScenario(JourneyScenario<SUBJECT> parentScenario) {
        this.parentScenario = parentScenario;
        return this;
    }
}
