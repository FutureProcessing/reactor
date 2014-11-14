package org.reactor.travelling.iotest;

import static org.reactor.travelling.JourneyScenarioBuilder.forSubject;

import com.google.common.base.Supplier;

import org.reactor.travelling.JourneyJournal;
import org.reactor.travelling.JourneyScenario;
import org.reactor.travelling.JourneyScenarioBuilder;
import org.reactor.travelling.TestForkingJourneyStep;
import org.reactor.travelling.TestJourneyStep1;
import org.reactor.travelling.TestJourneyStep3;
import org.reactor.travelling.TestRepeatingJourneyStep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedInputTestApplication {

    public static void main(String[] args) {
        new BufferedInputTestApplication().start();
    }

    private JourneyScenarioBuilder<StringBuffer> journeyScenarioBuilder;
    private StringBuffer scenarioSubject;
    private Supplier<String> stepInputSupplier;

    private void start() {
        scenarioSubject = givenScenarioSubject();
        journeyScenarioBuilder = givenJourneyBuilder(scenarioSubject);
        stepInputSupplier = givenStepInputSupplier();

        JourneyScenario<StringBuffer> journeyScenario = journeyScenarioBuilder.build();
        do {
            journeyScenario.answer(stepInputSupplier);
        } while (!journeyScenario.hasJourneyEnded());

        System.out.println(scenarioSubject.toString());
    }

    private JourneyJournal givenJournal() {
        return entryContents -> System.out.println("journal> " + entryContents);
    }

    private JourneyScenarioBuilder<StringBuffer> givenJourneyBuilder(StringBuffer scenarioSubject) {
        JourneyJournal journal = givenJournal();
        return forSubject(scenarioSubject)
            .journeyStep(new TestJourneyStep1(journal))
            .journeyStep(new TestRepeatingJourneyStep(journal))
            .journeyStep(new TestForkingJourneyStep(journal))
            .journeyStep(new TestJourneyStep3(journal));
    }

    private StringBuffer givenScenarioSubject() {
        return new StringBuffer();
    }

    private Supplier<String> givenStepInputSupplier() {
        return () -> {
            System.out.print(scenarioSubject.toString() + "> ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                return br.readLine();
            } catch (IOException ioe) {
                return null;
            }
        };
    }
}
