package org.reactor.response.list;

import org.reactor.response.StringReactorResponse;

import java.io.PrintWriter;

public abstract class ListReactorResponse<T> extends StringReactorResponse {

    public ListReactorResponse(String header) {
        super(header);
    }

    public ListReactorResponse() {
        super("");
    }

    @Override
    protected final void printResponse(PrintWriter responseWriter)  {
        ListElementFormatter<T> formatter = getElementFormatter();
        Iterable<T> listElements = getElements();

        int index = 0;
        for (T listElement : listElements) {
            responseWriter.println(formatter.formatListElement(index + 1, listElement));
            index++;
        }
    }
    
    protected abstract Iterable<T> getElements();
    
    protected abstract ListElementFormatter<T> getElementFormatter();
}
