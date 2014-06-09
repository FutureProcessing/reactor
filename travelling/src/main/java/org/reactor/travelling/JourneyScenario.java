package org.reactor.travelling;

import com.google.common.base.Supplier;
import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.JourneyStepVisitor;
import org.reactor.travelling.step.forking.ForkingStepOutcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class JourneyScenario<SUBJECT> implements JourneyStepVisitor<SUBJECT> {

    private final static Logger LOG = LoggerFactory.getLogger(JourneyScenario.class);

    private final SUBJECT scenarioSubject;
    private final List<AbstractJourneyStep<SUBJECT>> journeySteps;
    private final JourneyScenario<SUBJECT> parentScenario;

    private int stepIndex;
    private boolean journeyEnded;
    private JourneyScenario<SUBJECT> forkedScenario;

    public JourneyScenario(JourneyScenario<SUBJECT> parentScenario, SUBJECT scenarioSubject,
                           List<AbstractJourneyStep<SUBJECT>> journeySteps) {
        this.parentScenario = parentScenario;
        this.scenarioSubject = scenarioSubject;
        this.journeySteps = journeySteps;

        startJourney();
    }

    private void startJourney() {
        currentStep().beforeStep();
    }

    public boolean hasJourneyEnded() {
        return journeyEnded;
    }

    public void answer(String stepInput) {
        if (forkedScenario != null) {
            forkedScenario.answer(stepInput);
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

    private AbstractJourneyStep<SUBJECT> currentStep() {
        if (forkedScenario != null) {
            return forkedScenario.currentStep();
        }
        return journeySteps.get(stepIndex);
    }

    @Override
    public void fork(ForkingStepOutcome<SUBJECT> forkingStepOutcome) {
        this.forkedScenario = forkingStepOutcome.createForkedScenario(scenarioSubject, this);
    }

    @Override
    public void endJourney() {
        journeyEnded = true;
        if (parentScenario != null) {
            parentScenario.forkedScenarioEnded(this);
        }
    }

    private void forkedScenarioEnded(JourneyScenario<SUBJECT> journeyScenario) {
        if (forkedScenario.equals(journeyScenario)) {
            forkedScenario = null;
            moveForward().beforeStep();
        }
    }

    private boolean validateCanMoveForward() {
        if (stepIndex + 1 <= journeySteps.size()) {
            return true;
        }
        LOG.warn("Can not move any forward");
        return false;
    }

    private boolean validateCanMoveBackward() {
        if (stepIndex > 0) {
            return true;
        }
        LOG.warn("Can not move any backward");
        return false;
    }

}
