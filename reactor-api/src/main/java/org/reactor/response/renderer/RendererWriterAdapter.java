package org.reactor.response.renderer;

import java.io.IOException;
import java.io.Writer;

public class RendererWriterAdapter extends Writer {

    private final ReactorResponseRenderer responseRenderer;

    public RendererWriterAdapter(ReactorResponseRenderer responseRenderer) {
        this.responseRenderer = responseRenderer;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        responseRenderer.renderTextLine(new String(cbuf, off, len));
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {

    }
}
