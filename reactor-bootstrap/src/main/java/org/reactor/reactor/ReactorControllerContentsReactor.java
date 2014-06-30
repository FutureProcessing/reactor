package org.reactor.reactor;

import static java.lang.String.format;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.Reactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

@ReactOn(value = "help",
        description = "Prints out this information")
public class ReactorControllerContentsReactor extends AbstractAnnotatedReactor<Void> {

    private final ReactorController reactorController;

    public ReactorControllerContentsReactor(ReactorController reactorController) {
        super(Void.class);
        this.reactorController = reactorController;
    }

    @Override
    protected ReactorResponse doReact(ReactorRequest<Void> reactorRequest) {
        return new ListReactorResponse<Reactor>("List of all reactors") {

            @Override
            protected Iterable<Reactor> getElements() {
                return reactorController.getReactors();
            }

            @Override
            protected ListElementFormatter<Reactor> getElementFormatter() {
                return new ListElementFormatter<Reactor>() {

                    @Override
                    public String formatListElement(long elementIndex, Reactor listElement) {
                        return format("%d. %s - %s", elementIndex, listElement.getTriggeringExpression(),
                                listElement.getDescription());
                    }
                };
            }
        };
    }
}
