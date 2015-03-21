package org.reactor.nesting;

import java.util.List;
import org.reactor.Reactor;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ReactorsInformationResponse extends ListReactorResponse<String> {

    private final List<String> reactors;

    public ReactorsInformationResponse(List<Reactor> reactors) {
        this.reactors = reactors.stream().map((reactor) -> reactor.getTriggeringExpression() + " - " + reactor.getDescription()).collect(toList());
    }

    @Override
    public String toConsoleOutput() {
        return reactors.stream().collect(joining("\n"));
    }

    @Override
    protected final Iterable<String> getElements() {
        return reactors;
    }

    @Override
    protected final ListElementFormatter<String> getElementFormatter() {
        return (elementIndex, nestedReactor) -> nestedReactor;
    }
}
