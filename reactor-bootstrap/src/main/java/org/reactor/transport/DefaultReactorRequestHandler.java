package org.reactor.transport;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.interactive.InteractiveReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class DefaultReactorRequestHandler implements ReactorRequestHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultReactorRequestHandler.class);

    private static final int THREADS_COUNT = 10;

    private final ReactorController reactorController;
    private final ListeningExecutorService executorService;

    private InteractiveReactorRequestHandler interactiveHandler;

    public DefaultReactorRequestHandler(ReactorController reactorController) {
        this.reactorController = reactorController;
        this.executorService = listeningDecorator(newFixedThreadPool(THREADS_COUNT));

        prepareInteractiveRequestHandler();
    }

    private void prepareInteractiveRequestHandler() {
        LOGGER.debug("Preparing interactive request handler");
        this.interactiveHandler = new InteractiveReactorRequestHandler(reactorController, this::handleReactorRequest);
    }

    @Override
    public ListenableFuture<?> handleReactorRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer) {
        return executorService.submit(new HandleRequest(requestInput, sender, responseRenderer));
    }

    private class HandleRequest implements Runnable {
        private ReactorRequestInput requestInput;
        private String sender;
        private ReactorResponseRenderer responseRenderer;

        public HandleRequest(ReactorRequestInput requestInput, String sender, ReactorResponseRenderer responseRenderer) {
            this.requestInput = requestInput;
            this.sender = sender;
            this.responseRenderer = responseRenderer;
        }

        @Override
        public void run() {
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
}