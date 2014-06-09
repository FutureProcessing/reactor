package org.reactor.transport.directinput;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectInputProcessor {

    private final static Logger LOG = LoggerFactory.getLogger(DirectInputProcessor.class);
    private final ReactorMessageTransportProcessor messageProcessor;
    private boolean processing;

    public DirectInputProcessor(ReactorMessageTransportProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        startProcessing();
    }

    private void startProcessing() {
        LOG.debug("Starting direct input processing:");
        processing = true;
        new Thread(new DirectInputProcessingRunnable(new DirectInputListener() {

            @Override
            public void directInputGiven(String inputValue) {
                messageProcessor.processTransportMessage(inputValue, "DIRECT", new BufferedWriter(
                    new OutputStreamWriter(System.out)));
            }
        }) {

            @Override
            protected boolean isProcessing() {
                return super.isProcessing();
            }
        }).start();
    }

    public void stopProcessing() {
        processing = false;
    }
}
