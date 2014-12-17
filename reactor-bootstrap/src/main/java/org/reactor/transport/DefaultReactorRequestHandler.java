package org.reactor.transport;

import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.interactive.InteractiveReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

public class DefaultReactorRequestHandler implements ReactorRequestHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultReactorRequestHandler.class);

    private ReactorController reactorController;
    private InteractiveReactorRequestHandler interactiveHandler;

    public DefaultReactorRequestHandler(ReactorController reactorController) {
        this.reactorController = reactorController;

        prepareInteractiveRequestHandler();
    }

    private void prepareInteractiveRequestHandler() {
        LOGGER.debug("Preparing interactive request handler");
        interactiveHandler = new InteractiveReactorRequestHandler(reactorController, this::handleReactorRequest);
    }

    @Override
    public void handleReactorRequest(ReactorRequestInput requestInput, String sender,
                                     ReactorResponseRenderer responseRenderer) {
        if (requestInput.isInteractive()) {
            interactiveHandler.handleInteractiveRequest(requestInput.getArgumentsAsString(), sender, responseRenderer);
            return;
        }
        try {
            Optional<Reactor> reactor = reactorController.reactorMatchingInput(requestInput);
            if (reactor.isPresent()) {
                ReactorResponse response = reactor.get().react(sender, requestInput);
                response.renderResponse(responseRenderer);
                return;
            }
            LOGGER.warn("Unable to find reactor matching input: {}", requestInput.getArgumentsAsString());
        } catch (Exception e) {
            LOGGER.error("An error occurred while calling Reactor", e);
        }
    }
}
