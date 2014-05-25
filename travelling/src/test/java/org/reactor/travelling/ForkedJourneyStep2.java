package org.reactor.travelling;


import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class ForkedJourneyStep2 extends AbstractJourneyStep<StringBuffer> {

    @Override
    protected AbstractJourneyStepDirection doJourneyStep(String stepInput, StringBuffer journeySubject) {
        journeySubject.append("-BLE-");
        return finish();
    }

    @Override
    protected void doBeforeStep() {
        System.out.println("SECOND FORKED STEP");
    }
}
