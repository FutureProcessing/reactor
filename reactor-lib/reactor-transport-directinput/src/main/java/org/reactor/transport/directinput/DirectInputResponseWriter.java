package org.reactor.transport.directinput;

import java.io.IOException;

import org.reactor.writer.MultilineSplittingWriter;

public class DirectInputResponseWriter extends MultilineSplittingWriter {

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    protected void writeLine(String line) throws IOException {
        System.out.println(line);
    }
}
