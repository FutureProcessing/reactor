package org.reactor.travelling.step;

public abstract class AbstractJourneyStep<SUBJECT> {

    public abstract AbstractJourneyStepDirection doStep(String stepInput, SUBJECT journeySubject);

    public void doBeforeStep() {}

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> forward() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                journeyStepVisitor.moveForward();
            }
        };
    }

    protected final <SUBJECT> AbstractJourneyStepDirection<SUBJECT> repeat() {
        return new AbstractJourneyStepDirection<SUBJECT>() {

            @Override
            public void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor) {
                journeyStepVisitor.repeat();
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
                journeyStepVisitor.endJourney();
            }
        };
    }
}
