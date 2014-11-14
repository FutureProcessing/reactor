package org.reactor.travelling;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.travelling.JourneyScenarioBuilder.forSubject;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

public class JourneyScenarioBuilderTest extends AbstractUnitTest {

    private JourneyScenarioBuilder<StringBuffer> journeyScenarioBuilder;
    private StringBuffer scenarioSubject;

    @Test
    public void shouldFinishJourneyNormally() {
        // given
        scenarioSubject = givenScenarioSubject();
        journeyScenarioBuilder = givenNormalScenarioBuilder();

        // when
        JourneyScenario<StringBuffer> journeyScenario = journeyScenarioBuilder.build();
        journeyScenario.answer("first");
        journeyScenario.answer("second");
        journeyScenario.answer("third");

        // then
        assertThat(journeyScenario.hasJourneyEnded()).isTrue();
        assertThat(scenarioSubject.toString()).isEqualTo(" - first # second ! third");
    }

    @Test
    public void shouldFinishJourneyAfterFewIterationsOfStep() {
         // given
        scenarioSubject = givenScenarioSubject();
        journeyScenarioBuilder  = givenRepeatingScenarioBuilder();

        // when
        JourneyScenario<StringBuffer> journeyScenario = journeyScenarioBuilder.build();
        journeyScenario.answer("first");
        journeyScenario.answer("green");
        journeyScenario.answer("yellow");
        journeyScenario.answer("blue");
        journeyScenario.answer("silver");
        journeyScenario.answer("red");
        journeyScenario.answer("third");

        // then
        assertThat(journeyScenario.hasJourneyEnded()).isTrue();
        assertThat(scenarioSubject.toString()).isEqualTo(" - first # red ! third");
    }

    @Test
    public void shouldForkJourneyScenarioAndAppendTextAtProperPlace() {
        // given
        scenarioSubject = givenScenarioSubject();
        journeyScenarioBuilder = givenForkingScenarioBuilder();

        // when
        JourneyScenario<StringBuffer> journeyScenario = journeyScenarioBuilder.build();
        journeyScenario.answer("first");
        journeyScenario.answer("second");
        journeyScenario.answer("lol");
        journeyScenario.answer("whatever");
        journeyScenario.answer("whatever");
        journeyScenario.answer("third");

        // then
        assertThat(journeyScenario.hasJourneyEnded()).isTrue();
        assertThat(scenarioSubject.toString()).isEqualTo(" - first # second ! lol-TROLOLOLO--BLE-");
    }

    @Test
    public void shouldEndJourneyWhenThereAreNoSteps() {
        // given
        scenarioSubject = givenScenarioSubject();
        journeyScenarioBuilder = givenEmptyScenarioBuilder();

        // when
        JourneyScenario<StringBuffer> journeyScenario = journeyScenarioBuilder.build();
        journeyScenario.answer("anything");

        // then
        assertThat(journeyScenario.hasJourneyEnded()).isTrue();
    }

    private StringBuffer givenScenarioSubject() {
        return new StringBuffer();
    }

    private JourneyJournal givenJournal() {
        return entryContents -> System.out.println("journal> " + entryContents);
    }

    private JourneyScenarioBuilder<StringBuffer> givenForkingScenarioBuilder() {
        JourneyJournal journal = givenJournal();
        return forSubject(scenarioSubject)
                .journeyStep(new TestJourneyStep1(journal))
                .journeyStep(new TestJourneyStep2(journal))
                .journeyStep(new TestForkingJourneyStep(journal))
                .journeyStep(new TestJourneyStep3(journal));
    }

    private JourneyScenarioBuilder<StringBuffer> givenRepeatingScenarioBuilder() {
        JourneyJournal journal = givenJournal();
        return forSubject(scenarioSubject)
                .journeyStep(new TestJourneyStep1(journal))
                .journeyStep(new TestRepeatingJourneyStep(journal))
                .journeyStep(new TestJourneyStep3(journal));
    }

    private JourneyScenarioBuilder<StringBuffer> givenNormalScenarioBuilder() {
        JourneyJournal journal = givenJournal();
        return forSubject(scenarioSubject)
                .journeyStep(new TestJourneyStep1(journal))
                .journeyStep(new TestJourneyStep2(journal))
                .journeyStep(new TestJourneyStep3(journal));
    }

    private JourneyScenarioBuilder<StringBuffer> givenEmptyScenarioBuilder() {
        return forSubject(scenarioSubject);
    }
}
