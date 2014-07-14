package org.reactor.nesting;

import static java.lang.String.format;
import java.util.List;
import java.util.function.Supplier;
import org.reactor.Reactor;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

public class ReactorsInformationResponse extends ListReactorResponse<Reactor> {

    private final Supplier<List<Reactor>> reactorsSupplier;

    public ReactorsInformationResponse(Supplier<List<Reactor>> reactorsSupplier) {
        this.reactorsSupplier = reactorsSupplier;
    }

    public ReactorsInformationResponse(String header, Supplier<List<Reactor>> reactorsSupplier) {
        super(header);
        this.reactorsSupplier = reactorsSupplier;
    }

    @Override
    protected final Iterable<Reactor> getElements() {
        return reactorsSupplier.get();
    }

    @Override
    protected final ListElementFormatter<Reactor> getElementFormatter() {
        return (elementIndex, nestedReactor) -> format("%s. %s - %s", elementIndex, nestedReactor.getTriggeringExpression(),
                nestedReactor.getDescription());
    }
}
