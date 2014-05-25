package org.reactor.travelling.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJourneyStep<SUBJECT> {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractJourneyStep.class);
    
    public final AbstractJourneyStepDirection doStep(String stepInput, SUBJECT journeySubject) {
        return doJourneyStep(stepInput, journeySubject);
    }

    public final void beforeStep() {
        doBeforeStep();
    }

    protected void doBeforeStep() {

    }

    protected abstract AbstractJourneyStepDirection doJourneyStep(String stepInput, SUBJECT journeySubject);

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> forward() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                AbstractJourneyStep<SUBJECT> nextStep = journeyStepVisitor.moveForward();
                nextStep.beforeStep();
            }
        };
    }

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> repeat() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                // do nothing
            }
        };
    }

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> backward() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                journeyStepVisitor.moveBackward();
            }
        };
    }

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> finish() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                LOG.warn("Current journey has ended");
                journeyStepVisitor.endJourney();
            }
        };
    }
}
