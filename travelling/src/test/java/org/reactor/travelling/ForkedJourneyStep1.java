package org.reactor.travelling;


import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class ForkedJourneyStep1 extends AbstractJourneyStep<StringBuffer> {

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, StringBuffer journeySubject) {
        journeySubject.append("-TROLOLOLO-");
        return forward();
    }

    @Override
    public void doBeforeStep() {
        System.out.println("FIRST FORKED STEP");
    }
}
