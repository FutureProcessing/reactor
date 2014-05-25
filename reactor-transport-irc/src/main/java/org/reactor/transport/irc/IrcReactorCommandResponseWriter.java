package org.reactor.transport.irc;

import org.pircbotx.hooks.types.GenericEvent;
import org.reactor.writer.MultilineSplittingWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IrcReactorCommandResponseWriter extends MultilineSplittingWriter {

    private final Logger LOG = LoggerFactory.getLogger(IrcReactorCommandResponseWriter.class);

    private final GenericEvent event;

    public IrcReactorCommandResponseWriter(GenericEvent event) {
        this.event = event;
    }

    @Override
    public void writeLine(String filtered) {
        respond(filtered);
    }

    private void respond(String line) {
        LOG.debug("Responding: {}", line);
        event.respond(line);
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
