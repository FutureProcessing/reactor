package org.reactor;

import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "noparams")
public class TestNoParamsReactor extends AbstractAnnotatedReactor {

    @Override
    public ReactorResponse doReact(ReactorRequest request) {
        return new StringReactorResponse("empty");
    }

}
