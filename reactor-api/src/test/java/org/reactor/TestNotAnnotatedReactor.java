package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;

import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

public class TestNotAnnotatedReactor extends AbstractAnnotatedReactor {

    @Override
    protected ReactorResponse doReact(ReactorRequest reactorRequest) {
        return NO_RESPONSE;
    }
}
