package org.reactor.writer;

import static java.lang.System.lineSeparator;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

import java.io.IOException;
import java.io.PrintWriter;

public class BufferedMultilineSplittingWriterTest extends AbstractUnitTest {

    private BufferedMultilineSplittingWriter bufferedWriter;

    @Test
    public void shouldPrintManyLinesAtOneMethodCall() throws IOException {
        // given
        String multilineText = "first" + lineSeparator() + "second" + lineSeparator() + "third";
        bufferedWriter = givenBufferedMultilineSplittingWriter();

        // when
        new PrintWriter(bufferedWriter, true).println(multilineText);

        // then
        verify(bufferedWriter).writeLines(anyString());
    }

    private BufferedMultilineSplittingWriter givenBufferedMultilineSplittingWriter() {
        return spy(new BasicBufferedMultilineSplittingWriter());
    }

    private class BasicBufferedMultilineSplittingWriter extends BufferedMultilineSplittingWriter {

        @Override
        protected void writeLines(String lines) throws IOException {
            System.out.println(lines);
        }

        @Override
        public void close() throws IOException {
        }
    }

}
