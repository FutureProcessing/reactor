package org.reactor.jira.response;

import com.google.common.collect.Lists;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import java.util.List;


public class JiraListReactorResponse<T> extends ListReactorResponse<T> {

    private List<T> elements = Lists.newArrayList();
    private ListElementFormatter<T> formatter;
    
    private JiraListReactorResponse() {
    }
    
    @Override
    protected Iterable<T> getElements() {
        return elements;
    }

    @Override
    protected ListElementFormatter<T> getElementFormatter() {
        return formatter;
    }

    public JiraListReactorResponse(List<T> elements, ListElementFormatter<T> formatter) {
        this.elements = elements;
        this.formatter = formatter;
    }
    
}
