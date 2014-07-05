package org.reactor.transport.interactive;

import com.google.common.base.Optional;

import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.travelling.JourneyScenario;

import java.io.Writer;

public class InteractiveTransportMessageProcessor implements ReactorMessageTransportProcessor {

    @Override
    public void processTransportMessage(String messageText, String sender, Writer responseWriter) {
        Optional<JourneyScenario<?>> journey = findExistingJourney(sender);
        if (journey.isPresent()) {
            continueJourney(journey.get(), messageText);
        } else {
            startNewJourney(sender, messageText);
        }
        // TODO
    }

    private void startNewJourney(String traveler, String journeyStepInput) {

    }

    private void continueJourney(JourneyScenario<?> existingJourney, String journeyStepInput) {
        // TODO.
    }

    private Optional<JourneyScenario<?>> findExistingJourney(String traveler) {
        return null;// TODO.
    }
}
