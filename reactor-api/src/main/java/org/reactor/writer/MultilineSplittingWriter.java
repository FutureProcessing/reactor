package org.reactor.writer;

import static java.lang.System.lineSeparator;

import java.io.IOException;
import java.io.Writer;

public abstract class MultilineSplittingWriter extends Writer {

    private final static char[] NEW_LINE = lineSeparator().toCharArray();

    @Override
    public final void write(char[] cbuf, int off, int len) throws IOException {
        StringBuffer filteredBuffer = new StringBuffer();
        for (int charIndex = off; charIndex < off + len; charIndex++) {
            char character = cbuf[charIndex];
            if (matchesNewLine(cbuf, charIndex)) {
                writeSingleLine(filteredBuffer.toString());
                filteredBuffer = new StringBuffer();
                charIndex += NEW_LINE.length - 1;
                continue;
            }
            filteredBuffer.append(character);
        }
        writeSingleLine(filteredBuffer.toString());
    }

    private boolean matchesNewLine(char[] cbuf, int charIndex) {
        if (charIndex + NEW_LINE.length > cbuf.length) {
            return false;
        }
        for (int i = 0; i < NEW_LINE.length; i++) {
            if (cbuf[charIndex + i] == NEW_LINE[i]) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private void writeSingleLine(String line) throws IOException {
        if (line.length() == 0) {
            return;
        }
        writeLine(line);
    }

    protected abstract void writeLine(String line) throws IOException;
}
