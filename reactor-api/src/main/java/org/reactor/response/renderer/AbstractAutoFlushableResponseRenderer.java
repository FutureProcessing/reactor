package org.reactor.response.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public abstract class AbstractAutoFlushableResponseRenderer implements ReactorResponseRenderer {

    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractAutoFlushableResponseRenderer.class);

    @Override
    public final void commit(Writer responseWriter) {
        commitBeforeFlush(responseWriter);
        try {
            responseWriter.flush();
            responseWriter.close();
        } catch (IOException e) {
            LOGGER.error("Unable to commit writer buffer", e);
        }
    }

    protected abstract void commitBeforeFlush(Writer responseWriter);

}
