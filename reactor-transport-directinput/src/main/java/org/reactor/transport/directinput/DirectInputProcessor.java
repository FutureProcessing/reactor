package org.reactor.transport.directinput;

import org.reactor.transport.ReactorMessageTransportProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DirectInputProcessor {

    private static final String SENDER_DIRECT = "DIRECT";
    private static final Logger LOG = LoggerFactory.getLogger(DirectInputProcessor.class);
    private final ReactorMessageTransportProcessor messageProcessor;
    private final Executor executorService;
    private boolean processing;

    public DirectInputProcessor(ReactorMessageTransportProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        executorService = Executors.newSingleThreadExecutor();
        startProcessing();
    }

    private void startProcessing() {
        LOG.debug("Starting direct input processing:");
        processing = true;

        executorService.execute(new DirectInputProcessingRunnable(
                inputValue -> messageProcessor.processTransportMessage(inputValue, SENDER_DIRECT, new BufferedWriter(
                        new OutputStreamWriter(System.out)))) {

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
