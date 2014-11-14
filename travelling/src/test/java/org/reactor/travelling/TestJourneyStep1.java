package org.reactor.travelling;

import static java.lang.String.format;

import org.reactor.travelling.step.AbstractJourneyStep;
import org.reactor.travelling.step.AbstractJourneyStepDirection;

public class TestJourneyStep1 extends AbstractJourneyStep<StringBuffer> {

    private final JourneyJournal journal;

    public TestJourneyStep1(JourneyJournal journal) {
        this.journal = journal;
    }

    @Override
    public AbstractJourneyStepDirection<StringBuffer> doStep(String stepInput, StringBuffer journeySubject) {
        journal.logJournalEntry(format("Just entered: %s", stepInput));
        journeySubject.append(" - first");
        return forward();
    }

    @Override
    public void doBeforeStep() {
        journal.logJournalEntry("Please enter first value");
    }

}
