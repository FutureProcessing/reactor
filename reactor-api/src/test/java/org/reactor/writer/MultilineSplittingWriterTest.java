package org.reactor.writer;

import static java.lang.System.lineSeparator;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.io.PrintWriter;

public class MultilineSplittingWriterTest extends AbstractUnitTest {

    private MultilineSplittingWriter multilineWriter;

    @Test
    public void shouldPrintThreeLinesForMultilineString() throws IOException {
        // given
        String multilineText = "first" + lineSeparator() + "second" + lineSeparator() + "third";
        multilineWriter = spy(new BasicMultilineSplittingWriter());

        // when
        new PrintWriter(multilineWriter).println(multilineText);

        // then
        verify(multilineWriter, times(3)).writeLine(anyString());
    }

    private class BasicMultilineSplittingWriter extends MultilineSplittingWriter {

        @Override
        protected void writeLine(String line) throws IOException {
            System.out.println(">>> " + line);
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }
}
