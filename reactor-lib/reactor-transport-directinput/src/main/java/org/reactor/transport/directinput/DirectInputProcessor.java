package org.reactor.transport.directinput;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.Executor;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectInputProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DirectInputProcessor.class);

    private static final String SENDER_DIRECT = "DIRECT";
    private final ReactorRequestHandler messageProcessor;
    private final Executor executorService;
    private boolean processing;

    public DirectInputProcessor(ReactorRequestHandler messageProcessor) {
        this.messageProcessor = messageProcessor;
        executorService = newSingleThreadExecutor();
        startProcessing();
    }

    private void startProcessing() {
        LOG.debug("Starting direct input processing:");
        processing = true;

        executorService.execute(new DirectInputProcessingRunnable(inputValue -> {
            ReactorRequestInput requestInput = new ReactorRequestInput(inputValue);
            requestInput.setInteractive(true);

            ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
            messageProcessor.handleReactorRequest(requestInput, SENDER_DIRECT, responseRenderer);
            responseRenderer.commit(new DirectInputResponseWriter());
        }) {

            @Override
            protected boolean isProcessing() {
                return processing;
            }
        });
    }

    public void stopProcessing() {
        processing = false;
    }
}
