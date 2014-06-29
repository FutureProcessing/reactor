package org.reactor.command;

import static java.lang.String.format;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.AbstractNestingReactor;
import org.reactor.Reactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

@ReactOn(value = "help", description = "Prints out this information")
public class PrintNestedReactorsReactor extends AbstractAnnotatedReactor<Void> {

    private final AbstractNestingReactor nestingReactor;

    public PrintNestedReactorsReactor(AbstractNestingReactor nestingReactor) {
        super(Void.class);
        this.nestingReactor = nestingReactor;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> request) {
        return new ListReactorResponse<Reactor>(nestingReactor.getDescription()) {

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
        };

    }
}
