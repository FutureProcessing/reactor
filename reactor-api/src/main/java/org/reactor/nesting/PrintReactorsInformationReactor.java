package org.reactor.nesting;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.Reactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.CommandHelpResponse;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "help", description = "Prints out this information")
public class PrintReactorsInformationReactor extends AbstractAnnotatedReactor<String> {

    private static Predicate<Reactor> TRIGGER_EQUALS(String trigger) {
        return reactor -> reactor.getTriggeringExpression().equals(trigger);
    }

    private final Supplier<List<Reactor>> reactorsSupplier;

    public PrintReactorsInformationReactor(Supplier<List<Reactor>> reactorsSupplier) {
        super(String.class);
        this.reactorsSupplier = reactorsSupplier;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<String> request) {
        if (isNullOrEmpty(request.getRequestData())) {
            return new ReactorsInformationResponse("Available reactors", reactorsSupplier);
        }
        Optional<Reactor> subReactorOptional = reactorsSupplier.get().stream().filter(TRIGGER_EQUALS(request.getRequestData())).findFirst();
        if (subReactorOptional.isPresent()) {
            return prepareHelpResponse(subReactorOptional);
        }
        return NO_RESPONSE;
    }

    private ReactorResponse prepareHelpResponse(Optional<Reactor> reactorOptional) {
        Reactor reactor = reactorOptional.get();
        return new CommandHelpResponse(reactor.getDescription(), reactor);
    }
}
