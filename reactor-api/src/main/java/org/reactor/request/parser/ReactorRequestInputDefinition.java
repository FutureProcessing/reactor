package org.reactor.request.parser;

public class ReactorRequestInputDefinition {

    private final Class<?> type;

    public ReactorRequestInputDefinition(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
