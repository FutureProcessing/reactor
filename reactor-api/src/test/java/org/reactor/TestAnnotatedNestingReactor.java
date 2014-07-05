package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;

import org.reactor.annotation.ReactOn;
import org.reactor.data.ArgumentABCData;
import org.reactor.data.ArgumentAData;
import org.reactor.data.RequiredArgumentData;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(TestAnnotatedNestingReactor.TEST_REACTOR_TRIGGER)
public class TestAnnotatedNestingReactor extends AbstractNestingReactor {

    public final static String TEST_REACTOR_TRIGGER = "test";

    @ReactOn("noArguments")
    public ReactorResponse noArguments() {
        return NO_RESPONSE;
    }

    @ReactOn("singleArgument")
    public ReactorResponse singleArgument(ReactorRequest<ArgumentAData> reactorRequest) {
        return new StringReactorResponse(reactorRequest.getRequestData().getArgumentA());
    }

    @ReactOn("manyArguments")
    public ReactorResponse manyArguments(ReactorRequest<ArgumentABCData> reactorRequest) {
        return new StringReactorResponse(reactorRequest.getRequestData().getArgumentA() + " - "
                + reactorRequest.getRequestData().getArgumentB() + " - " + reactorRequest.getRequestData().getArgumentC());
    }

    public ReactorResponse noAnnotation() {
        return NO_RESPONSE;
    }

    @ReactOn("requiredArgument")
    public ReactorResponse requiredArgument(ReactorRequest<RequiredArgumentData> reactorRequest) {
        return new StringReactorResponse(reactorRequest.getRequestData().getRequiredArgument());
    }

    @ReactOn("wrongReturnType")
    public String wrongReturnType() {
        return "";
    }
}
