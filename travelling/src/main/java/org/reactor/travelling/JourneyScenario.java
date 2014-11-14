package org.reactor.travelling;

import com.google.common.base.Supplier;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.JourneyStepVisitor;
import org.reactor.travelling.step.forking.ForkingStepOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class JourneyScenario<SUBJECT> implements JourneyStepVisitor<SUBJECT> {

    private final static Logger LOGGER = LoggerFactory.getLogger(JourneyScenario.class);

    private final SUBJECT scenarioSubject;
    private final List<AbstractJourneyStep<SUBJECT>> journeySteps;
    private final JourneyScenario<SUBJECT> parentScenario;

    private int stepIndex;
    private boolean journeyEnded;
    private JourneyScenario<SUBJECT> forkedScenario;

    JourneyScenario(JourneyScenario<SUBJECT> parentScenario, SUBJECT scenarioSubject,
                           List<AbstractJourneyStep<SUBJECT>> journeySteps) {
        this.parentScenario = parentScenario;
        this.scenarioSubject = scenarioSubject;
        this.journeySteps = journeySteps;

        startJourney();
    }

    private void startJourney() {
        AbstractJourneyStep<SUBJECT> firstStep = currentStep();
        if (firstStep == null) {
            journeyEnded = true;
            return;
        }
        firstStep.doBeforeStep();
    }

    public boolean hasJourneyEnded() {
        return journeyEnded;
    }

    public void answer(String stepInput) {
        if (forkedScenario != null) {
            forkedScenario.answer(stepInput);
            return;
        }
        if (hasJourneyEnded()) {
            return;
        }
        currentStep().doStep(stepInput, scenarioSubject).followDirection(this);
    }

    public void answer(Supplier<String> stepInputSupplier) {
        answer(stepInputSupplier.get());
    }

    @Override
    public AbstractJourneyStep<SUBJECT> moveForward() {
        if (forkedScenario != null) {
            return forkedScenario.moveForward();
        }
        if (!validateCanMoveForward()) {
            endJourney();
            return currentStep();
        }
        stepIndex++;
        return currentStep();
    }

    @Override
    public AbstractJourneyStep<SUBJECT> moveBackward() {
        if (forkedScenario != null) {
            return forkedScenario.moveBackward();
        }
        if (!validateCanMoveBackward()) {
            return currentStep();
        }
        stepIndex--;
        return currentStep();
    }

    @Override
    public void repeat() {
        currentStep().doBeforeStep();
    }

    private AbstractJourneyStep<SUBJECT> currentStep() {
        if (forkedScenario != null) {
            return forkedScenario.currentStep();
        }
        if (journeySteps.isEmpty()) {
            return null;
        }
        return journeySteps.get(stepIndex);
    }

    @Override
    public void fork(ForkingStepOutcome<SUBJECT> forkingStepOutcome) {
        this.forkedScenario = forkingStepOutcome.createForkedScenario(scenarioSubject, this);
    }

    @Override
    public void endJourney() {
        LOGGER.debug("Ending journey");
        journeyEnded = true;
        if (parentScenario != null) {
            LOGGER.debug("Going back from forked scenario");
            parentScenario.forkedScenarioEnded(this);
        }
    }

    private void forkedScenarioEnded(JourneyScenario<SUBJECT> journeyScenario) {
        if (forkedScenario.equals(journeyScenario)) {
            forkedScenario = null;
            endJourney();
        }
    }

    private boolean validateCanMoveForward() {
        if (stepIndex + 1 >= journeySteps.size()) {
            LOGGER.warn("Can not move any forward");
            return false;
        }
        return true;
    }

    private boolean validateCanMoveBackward() {
        if (stepIndex > 0) {
            return true;
        }
        LOGGER.warn("Can not move any backward");
        return false;
    }

    public SUBJECT getSubject() {
        return scenarioSubject;
    }
}
