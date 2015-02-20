package org.reactor.jira.response;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

public class JiraListReactorResponse<T> extends ListReactorResponse<T> {

    private List<T> elements = newArrayList();
    private transient ListElementFormatter<T> formatter;

    public JiraListReactorResponse(List<T> elements, ListElementFormatter<T> formatter) {
        this.elements = elements;
        this.formatter = formatter;
    }

    @Override
    public String toConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            builder.append(formatter.formatListElement(i+1, elements.get(i))).append("\n");
        }
        return builder.toString();
    }

    @Override
    protected Iterable<T> getElements() {
        return elements;
    }

    @Override
    protected ListElementFormatter<T> getElementFormatter() {
        return formatter;
    }
}
