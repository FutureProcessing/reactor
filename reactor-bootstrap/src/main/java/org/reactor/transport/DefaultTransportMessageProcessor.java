package org.reactor.transport;

import static org.reactor.request.ReactorRequestParser.parseRequestFromLine;
import com.google.common.base.Supplier;
import java.io.Writer;
import org.reactor.Reactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTransportMessageProcessor implements ReactorMessageTransportProcessor {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultTransportMessageProcessor.class);
    private final Supplier<Reactor> reactorSupplier;

    public DefaultTransportMessageProcessor(Supplier<Reactor> reactorSupplier) {
        this.reactorSupplier = reactorSupplier;
    }

    @Override
    public void processTransportMessage(String messageText, String sender, Writer responseWriter) {
        ReactorRequest request = parseRequestFromLine(sender, messageText);
        try {
            ReactorResponse response = reactorSupplier.get().react(request);
            response.renderResponse(responseWriter);
        } catch (Exception e) {
            LOG.error("An error occurred while calling Reactor", e);
        }
    }
}
