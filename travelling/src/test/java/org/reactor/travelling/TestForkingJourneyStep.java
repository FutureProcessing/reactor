package org.reactor.travelling;

import static java.lang.String.format;

import org.reactor.travelling.step.AbstractJourneyStepDirection;
import org.reactor.travelling.step.forking.AbstractForkingJourneyStep;

public class TestForkingJourneyStep extends AbstractForkingJourneyStep<StringBuffer> {

    private final JourneyJournal journal;

    public TestForkingJourneyStep(JourneyJournal journal) {
        this.journal = journal;

        onStepInput("lol").journeyStep(new ForkedJourneyStep1()).journeyStep(new ForkedJourneyStep2());
    }

    @Override
    protected AbstractJourneyStepDirection<StringBuffer> doForkingJourneyStep(String stepInput, StringBuffer journeySubject) {
        journal.logJournalEntry(format("Just entered: %s", stepInput));
        journeySubject.append(" ! " + stepInput);

        return finish();
    }

    @Override
    protected void doBeforeStep() {
        journal.logJournalEntry("Please enter third and last value, type lol go go deeper");
    }
}
