package org.reactor.transport;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import java.io.Writer;
import org.reactor.Reactor;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTransportMessageProcessor implements ReactorMessageTransportProcessor {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultTransportMessageProcessor.class);
    private final Supplier<ReactorController> reactorControllerSupplier;

    public DefaultTransportMessageProcessor(Supplier<ReactorController> reactorControllerSupplier) {
        this.reactorControllerSupplier = reactorControllerSupplier;
    }

    @Override
    public void processTransportMessage(String messageText, String sender, Writer responseWriter) {
        try {
            ReactorRequestInput requestInput = new ReactorRequestInput(messageText);
            Optional<Reactor> reactor = reactorControllerSupplier.get().reactorMatchingInput(requestInput);
            if (reactor.isPresent()) {
                ReactorResponse response = reactor.get().react(sender, requestInput.popArguments());
                response.renderResponse(responseWriter);
                return;
            }
            LOG.warn("Unable to find reactor matching input: {}", messageText);
        } catch (Exception e) {
            LOG.error("An error occurred while calling Reactor", e);
        }
    }
}
