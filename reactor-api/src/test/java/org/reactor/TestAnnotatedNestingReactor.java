package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;
import org.reactor.annotation.ReactOn;
import org.reactor.response.ReactorResponse;

@ReactOn(TestAnnotatedNestingReactor.TEST_REACTOR_TRIGGER)
public class TestAnnotatedNestingReactor extends AbstractNestingReactor {

    public final static String TEST_REACTOR_TRIGGER = "!test";

    @ReactOn("noArguments")
    public ReactorResponse noArguments() {
        return NO_RESPONSE;
    }

    /*@ReactOn("singleArgument")
    public ReactorResponse singleArgument(@ReactorRequestParameter(name = "argumentA", shortName = "a") String argumentA) {
        return new StringReactorResponse(argumentA);
    }

    @ReactOn("manyArguments")
    public ReactorResponse manyArguments(@ReactorRequestParameter(name = "argumentA", shortName = "a") String argumentA,
                                   @ReactorRequestParameter(name = "argumentB", shortName = "b") int argumentB,
                                   @ReactorRequestParameter(name = "argumentC", shortName = "c") boolean argumentC) {
        return new StringReactorResponse(argumentA + " - " + argumentB + " - " + argumentC);
    } */

    public ReactorResponse noAnnotation() {
        return NO_RESPONSE;
    }

    /*@ReactOn("requiredArgument")
    public ReactorResponse requiredArgument(@ReactorRequestParameter(name = "requiredArgument", shortName = "r", required = true) String requiredArgument) {
        return new StringReactorResponse(requiredArgument);
    } */

    @ReactOn("wrongReturnType")
    public String wrongReturnType() {
        return "";
    }
}
