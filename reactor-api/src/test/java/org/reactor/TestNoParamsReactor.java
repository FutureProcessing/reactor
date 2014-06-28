package org.reactor;

import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "noparams")
public class TestNoParamsReactor extends AbstractReactor<Void> {

    public TestNoParamsReactor() {
        super(Void.class);
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> request) {
        return new StringReactorResponse("empty");
    }

}
