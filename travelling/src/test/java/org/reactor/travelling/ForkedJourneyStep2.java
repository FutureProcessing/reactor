package org.reactor.travelling;


import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class ForkedJourneyStep2 extends AbstractJourneyStep<StringBuffer> {

    @Override
    public AbstractJourneyStepDirection doStep(String stepInput, StringBuffer journeySubject) {
        journeySubject.append("-BLE-");
        return finish();
    }

    @Override
    public void doBeforeStep() {
        System.out.println("SECOND FORKED STEP");
    }
}
