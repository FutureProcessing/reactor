package org.reactor.response.renderer;

import org.reactor.annotation.ToBeDeleted;
import org.reactor.response.ReactorResponse;
import org.reactor.response.list.ListElementFormatter;

import java.io.Writer;

public interface ReactorResponseRenderer {

    String render(ReactorResponse source);

    @ToBeDeleted
    void renderTextLine(String lineId, String templateToBeRendered, Object... templateParameters);

    @ToBeDeleted
    void renderTextLine(String templateToBeRendered, Object... templateParameters);

    @ToBeDeleted
    <T> void renderListLine(String lineId, int index, T listElement, ListElementFormatter<T> formatter);

    @ToBeDeleted
    <T> void renderListLine(int index, T listElement, ListElementFormatter<T> formatter);

    @ToBeDeleted
    void renderHeadLine(String headerTemplateToBeRendered, Object... templateParameters);

    @ToBeDeleted
    void renderDoubleLine(String lineId, double doubleValue);

    @ToBeDeleted
    void renderDoubleLine(double doubleValue);

    @ToBeDeleted
    void renderLongLine(String lineId, long longValue);

    @ToBeDeleted
    void renderLongLine(long longValue);

    @ToBeDeleted
    void commit(Writer responseWriter);
}
