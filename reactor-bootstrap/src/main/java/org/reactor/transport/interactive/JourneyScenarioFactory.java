package org.reactor.transport.interactive;

import org.reactor.travelling.JourneyScenario;

public class JourneyScenarioFactory {

    private final static JourneyScenarioFactory INSTANCE = new JourneyScenarioFactory();

    public static final JourneyScenario getOrCreateJourneyInstance(String traveler) {
        JourneyScenario journeyScenario = INSTANCE.findJourney(traveler);
        return journeyScenario;
    }

    private JourneyScenario findJourney(String traveler) {
        return null;// TODO.
    }

    private JourneyScenarioFactory() {

    }
}
