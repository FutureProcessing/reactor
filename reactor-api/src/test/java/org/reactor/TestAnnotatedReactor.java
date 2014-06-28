package org.reactor;

import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = TestAnnotatedReactor.ANNOTATED_REACTOR_TRIGGER,
         description = TestAnnotatedReactor.ANNOTATED_REACTOR_DESCRIPTION)
public class TestAnnotatedReactor extends AbstractReactor<String> {

    public static final String ANNOTATED_REACTOR_DESCRIPTION = "Description";
    public static final String ANNOTATED_REACTOR_TRIGGER = "!test";

    public TestAnnotatedReactor() {
        super(String.class);
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<String> reactorRequest) {
        return new StringReactorResponse(reactorRequest.getRequestData());
    }
}
