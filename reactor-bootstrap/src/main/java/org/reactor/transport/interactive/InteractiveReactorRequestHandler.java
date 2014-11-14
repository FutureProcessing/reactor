package org.reactor.transport.interactive;

import static com.google.common.collect.Maps.newHashMap;
import static org.reactor.transport.interactive.JourneyScenarioFactory.prepareReactorJourneyScenario;

import java.io.Writer;
import java.util.Map;

import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ResponseRenderingException;
import org.reactor.response.StringReactorResponse;
import org.reactor.travelling.JourneyScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

public class InteractiveReactorRequestHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InteractiveReactorRequestHandler.class);
    private ReactorController reactorController;
    private ReactorJourneyScenarioListener journeyScenarioListener;

    private Map<String, JourneyScenario<ReactorRequestInput>> journeys = newHashMap();

    public InteractiveReactorRequestHandler(ReactorController reactorController,
                                            ReactorJourneyScenarioListener journeyScenarioListener) {
        this.reactorController = reactorController;
        this.journeyScenarioListener = journeyScenarioListener;
    }

    public void handleInteractiveRequest(String messageText, String sender, Writer responseWriter) {
        JourneyScenario<ReactorRequestInput> journey = findExistingJourney(sender);
        if (journey != null) {
            continueJourney(sender, journey, messageText);
        } else {
            journey = startNewJourney(sender, messageText, responseWriter);
            if (journey == null) {
                return;
            }
        }
        handleEndedScenario(sender, journey, responseWriter);
    }

    private JourneyScenario<ReactorRequestInput> startNewJourney(String traveler, String journeyReactorTrigger,
                                                                 Writer responseWriter) {
        LOGGER.debug("Starting new reactor journey ({}) for traveler: {}", journeyReactorTrigger, traveler);

        Optional<Reactor> journeyReactor = reactorController.reactorMatchingTrigger(journeyReactorTrigger);
        if (!journeyReactor.isPresent()) {
            respondReactorMissing(responseWriter, journeyReactorTrigger);
            return null;
        }
        JourneyScenario<ReactorRequestInput> journeyScenario = prepareReactorJourneyScenario(journeyReactor.get(),
            responseWriter);
        journeys.put(traveler, journeyScenario);
        return journeyScenario;
    }

    private void continueJourney(String traveler, JourneyScenario<ReactorRequestInput> existingJourney,
                                 String journeyStepInput) {
        LOGGER.debug("Continuing reactor journey for traveler: {} ", traveler);
        existingJourney.answer(journeyStepInput);
    }

    private void handleEndedScenario(String traveler, JourneyScenario<ReactorRequestInput> journeyScenario,
                                     Writer responseWriter) {
        if (verifyScenarioEnded(traveler, journeyScenario)) {
            LOGGER.debug("Processing generated reactor request input");
            journeys.remove(traveler);
            journeyScenarioListener.reactorJourneyEnded(journeyScenario.getSubject(), traveler, responseWriter);
        }
    }

    private boolean verifyScenarioEnded(String traveler, JourneyScenario<ReactorRequestInput> journeyScenario) {
        if (journeyScenario.hasJourneyEnded()) {
            LOGGER.debug("Journey scenario for traveler {} has ended", traveler);
            return true;
        }
        return false;
    }

    private JourneyScenario<ReactorRequestInput> findExistingJourney(String traveler) {
        return journeys.get(traveler);
    }

    private void respondReactorMissing(Writer responseWriter, String journeyReactorTrigger) {
        respond(responseWriter, "Unable to find reactor matching trigger: %s", journeyReactorTrigger);
    }

    private void respond(Writer responseWriter, String responseTemplate, String... responseParameters) {
        try {
            new StringReactorResponse(responseTemplate, responseParameters).renderResponse(responseWriter);
        } catch (ResponseRenderingException e) {
            LOGGER.error("An error occurred while sending response", e);
        }
    }
}
