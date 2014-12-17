package org.reactor.renderer;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.renderer.AbstractAutoFlushableResponseRenderer;

public class JSONReactorResponseRenderer extends AbstractAutoFlushableResponseRenderer {

    private static final String PROPERTY_HEADER = "header";
    private static final String PROPERTY_TEXT = "text";
    private static final String PROPERTY_DOUBLE = "double";
    private static final String PROPERTY_LONG = "long";
    private static final String PROPERTY_LIST = "list";

    private JSONObject jsonObject;

    public JSONReactorResponseRenderer() {
        prepareJSONObject();
    }

    private void prepareJSONObject() {
        jsonObject = new JSONObject();
    }

    @Override
    public void renderHeadLine(String headerTemplateToBeRendered, Object... templateParameters) {
        if (isNullOrEmpty(headerTemplateToBeRendered)) {
            return;
        }
        jsonObject.put(PROPERTY_HEADER, format(headerTemplateToBeRendered, templateParameters));
    }

    @Override
    public void renderTextLine(String lineId, String templateToBeRendered, Object... templateParameters) {
        jsonObject.put(lineId, format(templateToBeRendered, templateParameters));
    }

    @Override
    public void renderTextLine(String templateToBeRendered, Object... templateParameters) {
        renderTextLine(PROPERTY_TEXT, templateToBeRendered, templateParameters);
    }

    @Override
    public <T> void renderListLine(String lineId, int index, T listElement, ListElementFormatter<T> formatter) {
        JSONArray jsonArray = new JSONArray();
        if (jsonObject.has(lineId)) {
            jsonArray = jsonObject.getJSONArray(lineId);
        }
        jsonArray.put(formatter.formatListElement(index, listElement));
        jsonObject.put(lineId, jsonArray);
    }

    @Override
    public <T> void renderListLine(int index, T listElement, ListElementFormatter<T> formatter) {
        renderListLine(PROPERTY_LIST, index, listElement, formatter);
    }

    @Override
    public void renderDoubleLine(String lineId, double doubleValue) {
        jsonObject.put(lineId, doubleValue);
    }

    @Override
    public void renderDoubleLine(double doubleValue) {
        renderDoubleLine(PROPERTY_DOUBLE, doubleValue);
    }

    @Override
    public void renderLongLine(String lineId, long longValue) {
        jsonObject.put(lineId, longValue);
    }

    @Override
    public void renderLongLine(long longValue) {
        renderLongLine(PROPERTY_LONG, longValue);
    }

    @Override
    public void commitBeforeFlush(Writer responseWriter) {
        jsonObject.write(responseWriter);
    }
}
