package org.reactor.travelling.step;

public abstract class AbstractJourneyStepDirection<SUBJECT> {

    public abstract void followDirection(JourneyStepVisitor<SUBJECT> journeyStepVisitor);
}
