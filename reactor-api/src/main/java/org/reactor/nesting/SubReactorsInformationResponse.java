package org.reactor.nesting;

import static java.lang.String.format;
import org.reactor.AbstractNestingReactor;
import org.reactor.Reactor;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

public class SubReactorsInformationResponse extends ListReactorResponse<Reactor> {

    private final AbstractNestingReactor nestingReactor;

    public SubReactorsInformationResponse(AbstractNestingReactor nestingReactor) {
        this.nestingReactor = nestingReactor;
    }

    @Override
    protected Iterable<Reactor> getElements() {
        return nestingReactor.subReactors();
    }

    @Override
    protected ListElementFormatter<Reactor> getElementFormatter() {
        return new ListElementFormatter<Reactor>() {

            @Override
            public String formatListElement(long elementIndex, Reactor nestedReactor) {
                return format("%s. %s - %s", elementIndex, nestedReactor.getTriggeringExpression(),
                        nestedReactor.getDescription());
            }
        };
    }
}
