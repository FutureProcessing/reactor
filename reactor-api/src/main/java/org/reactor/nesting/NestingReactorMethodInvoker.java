package org.reactor.nesting;

import java.lang.reflect.Method;
import org.reactor.AbstractNestingReactor;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ExceptionReactorResponse;
import org.reactor.response.ReactorResponse;

public class NestingReactorMethodInvoker {

    private final AbstractNestingReactor nestingReactor;
    private final String nestedReactorMethodName;

    public NestingReactorMethodInvoker(AbstractNestingReactor nestingReactor, String nestedReactorMethodName) {
        this.nestingReactor = nestingReactor;
        this.nestedReactorMethodName = nestedReactorMethodName;
    }

    public ReactorResponse invoke(ReactorRequest<?> reactorRequest) {
        try {
            Method objectMethodToInvoke = fidMethodToInvoke();
            return (ReactorResponse) objectMethodToInvoke.invoke(nestingReactor, reactorRequest);
        } catch (Throwable genericException) {
            return new ExceptionReactorResponse(genericException);
        }
    }

    private Method fidMethodToInvoke() throws NoSuchMethodException {
        return nestingReactor.getClass().getMethod(nestedReactorMethodName, ReactorRequest.class);
    }

}
