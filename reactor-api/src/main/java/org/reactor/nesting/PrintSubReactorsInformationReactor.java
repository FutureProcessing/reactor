package org.reactor.nesting;

import org.reactor.AbstractAnnotatedReactor;
import org.reactor.AbstractNestingReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "help", description = "Prints out this information")
public class PrintSubReactorsInformationReactor extends AbstractAnnotatedReactor<Void> {

    private final AbstractNestingReactor nestingReactor;

    public PrintSubReactorsInformationReactor(AbstractNestingReactor nestingReactor) {
        super(Void.class);
        this.nestingReactor = nestingReactor;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> request) {
        return new SubReactorsInformationResponse(nestingReactor);
    }
}
