package org.reactor.transport;

import com.google.common.base.Optional;
import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.interactive.InteractiveReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class DefaultReactorRequestHandler implements ReactorRequestHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultReactorRequestHandler.class);

    private static final int THREADS_COUNT = 10;

    private final ReactorController reactorController;
    private final ExecutorService threadPool;

    private InteractiveReactorRequestHandler interactiveHandler;

    public DefaultReactorRequestHandler(ReactorController reactorController) {
        this.reactorController = reactorController;
        this.threadPool = newFixedThreadPool(THREADS_COUNT);

        prepareInteractiveRequestHandler();
    }

    private void prepareInteractiveRequestHandler() {
        LOGGER.debug("Preparing interactive request handler");
        this.interactiveHandler = new InteractiveReactorRequestHandler(reactorController, this::handleReactorRequest);
    }

    @Override
    public Future<String> handleReactorRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer) {
        return threadPool.submit(new HandleRequest(requestInput, sender, responseRenderer));
    }

    private class HandleRequest implements Callable<String> {
        private ReactorRequestInput requestInput;
        private String sender;
        private ReactorResponseRenderer responseRenderer;

        public HandleRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer) {
            this.requestInput = requestInput;
            this.sender = sender;
            this.responseRenderer = responseRenderer;
        }

        @Override
        public String call() {
            if (requestInput.isInteractive()) {
                interactiveHandler.handleInteractiveRequest(requestInput.getArgumentsAsString(), sender, responseRenderer);
            }
            try {
                Optional<Reactor> reactor = reactorController.reactorMatchingInput(requestInput);
                if (reactor.isPresent()) {
                    ReactorResponse response = reactor.get().react(sender, requestInput);
                    response.renderResponse(responseRenderer); // legacy
                    return responseRenderer.render(response);
                }
                LOGGER.warn("Unable to find reactor matching input: {}", requestInput.getArgumentsAsString());
            } catch (Exception e) {
                LOGGER.error("An error occurred while calling Reactor", e);
            }
            throw new IllegalStateException("Expected request to be handled by now");
        }
    }
}
