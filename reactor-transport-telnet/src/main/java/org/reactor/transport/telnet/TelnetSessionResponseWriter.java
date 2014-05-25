package org.reactor.transport.telnet;

import org.apache.mina.core.session.IoSession;
import org.reactor.writer.MultilineSplittingWriter;

import java.io.IOException;

public class TelnetSessionResponseWriter extends MultilineSplittingWriter {

    private final IoSession session;

    public TelnetSessionResponseWriter(IoSession session) {
        this.session = session;
    }

    @Override
    protected void writeLine(String line) {
        session.write(line);
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
