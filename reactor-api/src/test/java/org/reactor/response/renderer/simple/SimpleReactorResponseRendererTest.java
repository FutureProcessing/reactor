package org.reactor.response.renderer.simple;

import static java.lang.String.format;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.StringWriter;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

public class SimpleReactorResponseRendererTest extends AbstractUnitTest {

    private static final String HEADER_TEMPLATE = "Header template, param1 = %s, param2 = %s";
    private static final String HEADER_PARAM_1 = "param1";
    private static final String HEADER_PARAM_2 = "param2";
    private static final String LONG_LINE_1 = "longLine1";
    private static final String LONG_LINE_2 = "longLine2";
    private static final long LONG_VALUE_1 = 100L;
    private static final long LONG_VALUE_2 = 200L;
    private static final String TEXT_LINE_1 = "textLine1";
    private static final String TEXT_VALUE_1 = "text1";
    private static final String TEXT_LINE_2 = "textLine2";
    private static final String TEXT_VALUE_2 = "text2";
    private static final String DOUBLE_LINE_1 = "doubleLine1";
    private static final String DOUBLE_LINE_2 = "doubleLine2";
    private static final double DOUBLE_VALUE_1 = 1.1d;
    private static final double DOUBLE_VALUE_2 = 2.2d;

    @Test
    public void shouldPrintHeaderIfNotNull() {
        // given
        SimpleReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();

        // when
        responseRenderer.renderHeadLine(HEADER_TEMPLATE, HEADER_PARAM_1, HEADER_PARAM_2);
        StringWriter writer = spy(new StringWriter());
        responseRenderer.commit(writer);

        // then
        String header = format(HEADER_TEMPLATE, HEADER_PARAM_1, HEADER_PARAM_2);
        verify(writer).write(header, 0, header.length());
    }

    @Test
    public void shouldNotPrintHeaderIfNull() {
        // given
        SimpleReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();

        // when
        StringWriter writer = spy(new StringWriter());
        responseRenderer.commit(writer);

        // then
        verify(writer, never()).write(anyString(), anyInt(), anyInt());
    }


    @Test
    public void shouldHaveAllEntriesPrintedOut() {
        // given
        SimpleReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();

        // when render lines
        responseRenderer.renderLongLine(LONG_LINE_1, LONG_VALUE_1);
        responseRenderer.renderLongLine(LONG_LINE_2, LONG_VALUE_2);
        responseRenderer.renderTextLine(TEXT_LINE_1, TEXT_VALUE_1);
        responseRenderer.renderTextLine(TEXT_LINE_2, TEXT_VALUE_2);
        responseRenderer.renderDoubleLine(DOUBLE_LINE_1, DOUBLE_VALUE_1);
        responseRenderer.renderDoubleLine(DOUBLE_LINE_2, DOUBLE_VALUE_2);

        // and after commit to writer
        StringWriter writer = spy(new StringWriter());
        responseRenderer.commit(writer);

        // then
        verify(writer).write(Long.toString(LONG_VALUE_1), 0, Long.toString(LONG_VALUE_1).length());
        verify(writer).write(Long.toString(LONG_VALUE_2), 0, Long.toString(LONG_VALUE_2).length());
        verify(writer).write(TEXT_VALUE_1, 0, TEXT_VALUE_1.length());
        verify(writer).write(TEXT_VALUE_2, 0, TEXT_VALUE_2.length());
        verify(writer).write(Double.toString(DOUBLE_VALUE_1), 0, Double.toString(DOUBLE_VALUE_1).length());
        verify(writer).write(Double.toString(DOUBLE_VALUE_2), 0, Double.toString(DOUBLE_VALUE_2).length());
    }

    @Test
    public void shouldPrintOutOnlyUniqueValuesForDefaultKeys() {
        // given
        SimpleReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();

        // when render lines
        responseRenderer.renderLongLine(LONG_VALUE_1);
        responseRenderer.renderLongLine(LONG_VALUE_2);
        responseRenderer.renderTextLine(TEXT_VALUE_1);
        responseRenderer.renderTextLine(TEXT_VALUE_2);
        responseRenderer.renderDoubleLine(DOUBLE_VALUE_1);
        responseRenderer.renderDoubleLine(DOUBLE_VALUE_2);

        // and after commit to writer
        StringWriter writer = spy(new StringWriter());
        responseRenderer.commit(writer);

        // then
        verify(writer).write(Long.toString(LONG_VALUE_2), 0, Long.toString(LONG_VALUE_2).length());
        verify(writer).write(TEXT_VALUE_2, 0, TEXT_VALUE_2.length());
        verify(writer).write(Double.toString(DOUBLE_VALUE_2), 0, Double.toString(DOUBLE_VALUE_2).length());
    }

}
