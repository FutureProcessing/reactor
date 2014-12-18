package org.reactor.transport.interactive;

import static com.google.common.collect.Maps.newHashMap;
import static org.reactor.transport.interactive.JourneyScenarioFactory.prepareReactorJourneyScenario;

import java.util.Map;

import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ResponseRenderingException;
import org.reactor.response.StringReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
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

    public void handleInteractiveRequest(String messageText, String sender, ReactorResponseRenderer responseRenderer) {
        JourneyScenario<ReactorRequestInput> journey = findExistingJourney(sender);
        if (journey != null) {
            continueJourney(sender, journey, messageText);
        } else {
            journey = startNewJourney(sender, messageText, responseRenderer);
            if (journey == null) {
                return;
            }
        }
        handleEndedScenario(sender, journey, responseRenderer);
    }

    private JourneyScenario<ReactorRequestInput> startNewJourney(String traveler, String journeyReactorTrigger,
                                                                 ReactorResponseRenderer responseRenderer) {
        LOGGER.debug("Starting new reactor journey ({}) for traveler: {}", journeyReactorTrigger, traveler);

        Optional<Reactor> journeyReactor = reactorController.reactorMatchingTrigger(journeyReactorTrigger);
        if (!journeyReactor.isPresent()) {
            respondReactorMissing(responseRenderer, journeyReactorTrigger);
            return null;
        }
        JourneyScenario<ReactorRequestInput> journeyScenario = prepareReactorJourneyScenario(journeyReactor.get(),
            responseRenderer);
        journeys.put(traveler, journeyScenario);
        return journeyScenario;
    }

    private void continueJourney(String traveler, JourneyScenario<ReactorRequestInput> existingJourney,
                                 String journeyStepInput) {
        LOGGER.debug("Continuing reactor journey for traveler: {} ", traveler);
        existingJourney.answer(journeyStepInput);
    }

    private void handleEndedScenario(String traveler, JourneyScenario<ReactorRequestInput> journeyScenario,
                                     ReactorResponseRenderer responseRenderer) {
        if (verifyScenarioEnded(traveler, journeyScenario)) {
            LOGGER.debug("Processing generated reactor request input");
            journeys.remove(traveler);
            journeyScenarioListener.reactorJourneyEnded(journeyScenario.getSubject(), traveler, responseRenderer);
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

    private void respondReactorMissing(ReactorResponseRenderer responseRenderer, String journeyReactorTrigger) {
        respond(responseRenderer, "Unable to find reactor matching trigger: %s", journeyReactorTrigger);
    }

    private void respond(ReactorResponseRenderer responseRenderer, String responseTemplate,
                         String... responseParameters) {
        try {
            new StringReactorResponse(responseTemplate, responseParameters).renderResponse(responseRenderer);
        } catch (ResponseRenderingException e) {
            LOGGER.error("An error occurred while sending response", e);
        }
    }
}
