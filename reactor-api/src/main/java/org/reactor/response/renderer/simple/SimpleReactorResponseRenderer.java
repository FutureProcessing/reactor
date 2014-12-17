package org.reactor.response.renderer.simple;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.String.format;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.renderer.AbstractAutoFlushableResponseRenderer;

public class SimpleReactorResponseRenderer extends AbstractAutoFlushableResponseRenderer {

    private String header;
    private List<String> responseElements = newLinkedList();

    @Override
    public void renderHeadLine(String headerTemplateToBeRendered, Object... templateParameters) {
        header = format(headerTemplateToBeRendered, templateParameters);
    }

    @Override
    public void renderTextLine(String lineId, String templateToBeRendered, Object... templateParameters) {
        renderTextLine(templateToBeRendered, templateParameters);
    }

    @Override
    public void renderTextLine(String templateToBeRendered, Object... templateParameters) {
        responseElements.add(format(templateToBeRendered, templateParameters));
    }

    @Override
    public <T> void renderListLine(String lineId, int index, T listElement, ListElementFormatter<T> formatter) {
        renderListLine(index, listElement, formatter);
    }

    @Override
    public <T> void renderListLine(int index, T listElement, ListElementFormatter<T> formatter) {
        responseElements.add(formatter.formatListElement(index, listElement));
    }

    @Override
    public void renderDoubleLine(String lineId, double doubleValue) {
        renderDoubleLine(doubleValue);
    }

    @Override
    public void renderDoubleLine(double doubleValue) {
        responseElements.add(Double.toString(doubleValue));
    }

    @Override
    public void renderLongLine(String lineId, long longValue) {
        renderLongLine(longValue);
    }

    @Override
    public void renderLongLine(long longValue) {
        responseElements.add(Long.toString(longValue));
    }

    @Override
    public void commitBeforeFlush(Writer responseWriter) {
        PrintWriter printWriter = new PrintWriter(responseWriter);
        if (!isNullOrEmpty(header)) {
            printWriter.print(header);
        }
        for (String responseElement : responseElements) {
            printWriter.print(responseElement);
        }
    }
}
