package org.reactor.travelling;


import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class ForkedJourneyStep1 extends AbstractJourneyStep<StringBuffer> {

    @Override
    protected AbstractJourneyStepDirection doJourneyStep(String stepInput, StringBuffer journeySubject) {
        journeySubject.append("-TROLOLOLO-");
        return forward();
    }

    @Override
    protected void doBeforeStep() {
        System.out.println("FIRST FORKED STEP");
    }
}
