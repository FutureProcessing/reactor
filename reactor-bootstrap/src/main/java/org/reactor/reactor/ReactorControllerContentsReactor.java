package org.reactor.reactor;

import static java.lang.String.format;
import org.reactor.AbstractNestingReactor;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.InitializingReactor;
import org.reactor.Reactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

@ReactOn(value = "!hub",
        description = "Reactor that gather a set of different reactors and merges them into one ;)")
public class ReactorControllerContentsReactor extends AbstractNestingReactor implements InitializingReactor {

    private final ReactorController reactorController;

    public ReactorControllerContentsReactor(ReactorController reactorController) {
        this.reactorController = reactorController;
    }

    @Override
    public void initReactor(ReactorProperties reactorProperties) {
        registerNestedReactor(new ListHubReactorsReactor());
    }

    @ReactOn("list")
    private class ListHubReactorsReactor extends AbstractAnnotatedReactor<Void> {

        public ListHubReactorsReactor() {
            super(Void.class);
        }

        @Override
        public ReactorResponse doReact(ReactorRequest<Void> request) {
            return new ListReactorResponse<Reactor>("List of all registered reactors") {

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
}
