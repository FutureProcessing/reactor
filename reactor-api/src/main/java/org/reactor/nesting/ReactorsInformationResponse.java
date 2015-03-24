package org.reactor.nesting;

import static java.lang.String.format;
import java.util.List;
import org.reactor.Reactor;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

public class ReactorsInformationResponse extends ListReactorResponse<Reactor> {

    private final List<Reactor> reactors;

    public ReactorsInformationResponse(List<Reactor> reactors) {
        this.reactors = reactors;
    }

    public ReactorsInformationResponse(String header, List<Reactor> reactors) {
        super(header);
        this.reactors = reactors;
    }

    @Override
    protected final Iterable<Reactor> getElements() {
        return reactors;
    }

    @Override
    protected final ListElementFormatter<Reactor> getElementFormatter() {
        return (elementIndex, nestedReactor) -> format("%s. %s - %s", elementIndex + 1, nestedReactor.getTriggeringExpression(),
                nestedReactor.getDescription());
    }
}
