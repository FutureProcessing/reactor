package org.reactor.travelling;

import static java.lang.String.format;

import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class TestRepeatingJourneyStep extends AbstractJourneyStep<StringBuffer> {

    private final JourneyJournal journal;

    public TestRepeatingJourneyStep(JourneyJournal journal) {
        this.journal = journal;
    }

    @Override
    protected void doBeforeStep() {
        journal.logJournalEntry("Getting a little deeper? pass a color name i'm thinking about to pass through :P");
    }

    @Override
    public AbstractJourneyStepDirection<StringBuffer> doJourneyStep(String stepInput, StringBuffer journeySubject) {
        if (stepInput.equals("red")) {
            journal.logJournalEntry(format("%s is pretty :)", stepInput));
            journeySubject.append(" # " + stepInput);
            return forward();
        }

        journal.logJournalEntry(format("%s is not pretty at all...", stepInput));
        return repeat();
    }
}
