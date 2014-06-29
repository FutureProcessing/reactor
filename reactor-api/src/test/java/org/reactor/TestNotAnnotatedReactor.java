package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

public class TestNotAnnotatedReactor extends AbstractAnnotatedReactor<Void> {

    public TestNotAnnotatedReactor() {
        super(Void.class);
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> reactorRequest) {
        return NO_RESPONSE;
    }
}
