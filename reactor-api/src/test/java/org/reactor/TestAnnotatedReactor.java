package org.reactor;

import static com.google.common.base.Joiner.on;

import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = TestAnnotatedReactor.ANNOTATED_REACTOR_TRIGGER,
         description = TestAnnotatedReactor.ANNOTATED_REACTOR_DESCRIPTION)
public class TestAnnotatedReactor extends AbstractAnnotatedReactor {

    public static final String ANNOTATED_REACTOR_DESCRIPTION = "Description";
    public static final String ANNOTATED_REACTOR_TRIGGER = "!test";

    @Override
    protected ReactorResponse doReact(ReactorRequest reactorRequest) {
        return new StringReactorResponse(on(',').join(reactorRequest.getArguments()));
    }
}
