package org.reactor.response.renderer;

import org.reactor.response.list.ListElementFormatter;

import java.io.Writer;

public interface ReactorResponseRenderer {

    void renderTextLine(String lineId, String templateToBeRendered, Object... templateParameters);

    void renderTextLine(String templateToBeRendered, Object... templateParameters);

    <T> void renderListLine(String lineId, int index, T listElement, ListElementFormatter<T> formatter);

    <T> void renderListLine(int index, T listElement, ListElementFormatter<T> formatter);

    void renderHeadLine(String headerTemplateToBeRendered, Object... templateParameters);

    void renderDoubleLine(String lineId, double doubleValue);

    void renderDoubleLine(double doubleValue);

    void renderLongLine(String lineId, long longValue);

    void renderLongLine(long longValue);

    void commit(Writer responseWriter);
}
