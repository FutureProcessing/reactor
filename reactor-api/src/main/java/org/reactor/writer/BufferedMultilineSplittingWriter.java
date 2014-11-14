package org.reactor.writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class BufferedMultilineSplittingWriter extends MultilineSplittingWriter {

    private StringWriter buffer = new StringWriter();
    private PrintWriter bufferedWriter = new PrintWriter(buffer);

    @Override
    protected final void writeLine(String line) throws IOException {
        bufferedWriter.println(line);
    }

    @Override
    public final void flush() throws IOException {
        writeLines(buffer.toString());

        buffer = new StringWriter();
        bufferedWriter = new PrintWriter(buffer);
    }

    protected abstract void writeLines(String lines) throws IOException;
}
